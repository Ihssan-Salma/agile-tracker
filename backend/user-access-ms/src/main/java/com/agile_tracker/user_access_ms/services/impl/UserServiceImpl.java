package com.agile_tracker.user_access_ms.services.impl;

import com.agile_tracker.user_access_ms.dtos.users.UserResponse;
import com.agile_tracker.user_access_ms.dtos.users.UserUpdateRequest;
import com.agile_tracker.user_access_ms.exceptions.BadRequestException;
import com.agile_tracker.user_access_ms.exceptions.NotFoundException;
import com.agile_tracker.user_access_ms.models.Utilisateur;
import com.agile_tracker.user_access_ms.repositories.UtilisateurRepository;
import com.agile_tracker.user_access_ms.services.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAllUsers() {
        return utilisateurRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Integer id) {
        return toResponse(getUtilisateur(id));
    }

    @Override
    public UserResponse updateUser(Integer id, UserUpdateRequest request) {
        Utilisateur utilisateur = getUtilisateur(id);

        if (request.getEmail() != null && !request.getEmail().equals(utilisateur.getEmail())) {
            if (utilisateurRepository.existsByEmail(request.getEmail())) {
                throw new BadRequestException("Email already in use");
            }
            utilisateur.setEmail(request.getEmail());
        }

        if (request.getUsername() != null && !request.getUsername().equals(utilisateur.getUsername())) {
            if (utilisateurRepository.existsByUsername(request.getUsername())) {
                throw new BadRequestException("Username already in use");
            }
            utilisateur.setUsername(request.getUsername());
        }

        if (request.getNom() != null) {
            utilisateur.setNom(request.getNom());
        }

        if (request.getPrenom() != null) {
            utilisateur.setPrenom(request.getPrenom());
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            utilisateur.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getCompetences() != null) {
            utilisateur.setCompetences(request.getCompetences());
        }

        if (request.getCapaciteHebdo() != null) {
            utilisateur.setCapaciteHebdo(request.getCapaciteHebdo());
        }

        if (request.getDisponibilite() != null) {
            utilisateur.setDisponibilite(request.getDisponibilite());
        }

        return toResponse(utilisateurRepository.save(utilisateur));
    }

    @Override
    public void deleteUser(Integer id) {
        Utilisateur utilisateur = getUtilisateur(id);
        utilisateurRepository.delete(utilisateur);
    }

    private Utilisateur getUtilisateur(Integer id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private UserResponse toResponse(Utilisateur utilisateur) {
        return UserResponse.builder()
                .id(utilisateur.getId())
                .nom(utilisateur.getNom())
                .prenom(utilisateur.getPrenom())
                .email(utilisateur.getEmail())
                .username(utilisateur.getUsername())
                .competences(utilisateur.getCompetences())
                .capaciteHebdo(utilisateur.getCapaciteHebdo())
                .disponibilite(utilisateur.getDisponibilite())
                .build();
    }
}
