package com.agile_tracker.user_access_ms.services.impl;

import com.agile_tracker.user_access_ms.clients.ProjectClient;
import com.agile_tracker.user_access_ms.dtos.invites.InviteUserRequest;
import com.agile_tracker.user_access_ms.dtos.invites.InviteUserResponse;
import com.agile_tracker.user_access_ms.dtos.projects.UserAssignmentDTO;
import com.agile_tracker.user_access_ms.models.Utilisateur;
import com.agile_tracker.user_access_ms.repositories.UtilisateurRepository;
import com.agile_tracker.user_access_ms.services.InviteNotificationService;
import com.agile_tracker.user_access_ms.services.InviteService;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InviteServiceImpl implements InviteService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final InviteNotificationService inviteNotificationService;
    private final ProjectClient projectClient; // The Feign Client bridge

    @Override
    @Transactional
    public InviteUserResponse inviteUser(InviteUserRequest request) {
        // 1. Logic: Check if the user already exists
        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail()).orElse(null);
        boolean isNewUser = false;
        String rawPassword = null;

        if (utilisateur == null) {
            isNewUser = true;
            rawPassword = request.getPassword();
            String username = resolveUsername(request.getUsername(), request.getEmail());

            // Map ALL data from request (images fields included)
            utilisateur = Utilisateur.builder()
                    .nom(request.getNom()) // Real name from request
                    .prenom(request.getPrenom()) // Real prenom from request
                    .email(request.getEmail())
                    .username(username)
                    .passwordHash(passwordEncoder.encode(rawPassword))
                    .competences(request.getCompetences())
                    .capaciteHebdo(resolveFloat(request.getCapaciteHebdo()))
                    .disponibilite(resolveFloat(request.getDisponibilite()))
                    .build();

            utilisateur = utilisateurRepository.save(utilisateur);
        }

        // 2. Logic: Internal Linkage to ms-project
        // We do this for both new and existing users
        try {
            UserAssignmentDTO assignment = new UserAssignmentDTO(
                    utilisateur.getId(),
                    request.getProjetId(),
                    request.getRole()
            );
            projectClient.assignUserToProject(assignment);
        } catch (Exception e) {
            // Log the error but don't fail the invitation.
            // This is "Linkage Ready" logic.
            log.warn("Linkage to ms-project failed: Service might be offline.");
        }

        // 3. Logic: Send Notification with context (Project + Role)
        inviteNotificationService.sendInvite(
                utilisateur.getEmail(),
                utilisateur.getUsername(),
                rawPassword, // Only sent if user was just created
                request.getProjectName(),
                request.getRole()
        );

        return InviteUserResponse.builder()
                .userId(utilisateur.getId())
                .email(utilisateur.getEmail())
                .username(utilisateur.getUsername())
                .created(isNewUser)
                .build();
    }

    private String resolveUsername(String username, String email) {
        String base = (username == null || username.isBlank()) ? extractLocalPart(email) : username;
        base = base.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9._-]", "");
        if (base.isBlank()) base = "user";

        String candidate = base;
        int counter = 1;
        while (utilisateurRepository.existsByUsername(candidate)) {
            candidate = base + counter++;
        }
        return candidate;
    }

    private String extractLocalPart(String email) {
        int atIndex = email.indexOf('@');
        return atIndex > 0 ? email.substring(0, atIndex) : email;
    }

    private Float resolveFloat(Float value) {
        return value == null ? 0.0f : value;
    }
}