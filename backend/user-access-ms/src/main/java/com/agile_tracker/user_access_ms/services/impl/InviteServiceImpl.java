package com.agile_tracker.user_access_ms.services.impl;

import com.agile_tracker.user_access_ms.dtos.invites.InviteUserRequest;
import com.agile_tracker.user_access_ms.dtos.invites.InviteUserResponse;
import com.agile_tracker.user_access_ms.models.Utilisateur;
import com.agile_tracker.user_access_ms.repositories.UtilisateurRepository;
import com.agile_tracker.user_access_ms.services.InviteNotificationService;
import com.agile_tracker.user_access_ms.services.InviteService;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InviteServiceImpl implements InviteService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final InviteNotificationService inviteNotificationService;

    @Override
    public InviteUserResponse inviteUser(InviteUserRequest request) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(request.getEmail()).orElse(null);
        boolean created = false;
        String rawPassword = null;

        if (utilisateur == null) {
            rawPassword = request.getPassword();
            String username = resolveUsername(request.getUsername(), request.getEmail());
            utilisateur = Utilisateur.builder()
                    .nom(extractLocalPart(request.getEmail()))
                    .prenom("Invited")
                    .email(request.getEmail())
                    .username(username)
                .passwordHash(passwordEncoder.encode(rawPassword))
                .competences(request.getCompetences())
                .capaciteHebdo(resolveFloat(request.getCapaciteHebdo()))
                .disponibilite(resolveFloat(request.getDisponibilite()))
                    .build();
            utilisateur = utilisateurRepository.save(utilisateur);
            created = true;
        }

        inviteNotificationService.sendInvite(
                utilisateur.getEmail(),
            utilisateur.getUsername(),
            rawPassword
        );

        return InviteUserResponse.builder()
                .userId(utilisateur.getId())
                .email(utilisateur.getEmail())
                .username(utilisateur.getUsername())
                .created(created)
                .build();
    }

    private String resolveUsername(String username, String email) {
        String base = username;
        if (base == null || base.isBlank()) {
            base = extractLocalPart(email);
        }
        base = base.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9._-]", "");
        if (base.isBlank()) {
            base = "user";
        }
        String candidate = base;
        int counter = 1;
        while (utilisateurRepository.existsByUsername(candidate)) {
            candidate = base + counter;
            counter++;
        }
        return candidate;
    }

    private String extractLocalPart(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex > 0) {
            return email.substring(0, atIndex);
        }
        return email;
    }

    private Float resolveFloat(Float value) {
        return value == null ? 0.0f : value;
    }
}
