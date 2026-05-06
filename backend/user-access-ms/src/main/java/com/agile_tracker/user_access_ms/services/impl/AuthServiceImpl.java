package com.agile_tracker.user_access_ms.services.impl;

import com.agile_tracker.user_access_ms.dtos.auth.AuthTokens;
import com.agile_tracker.user_access_ms.dtos.auth.LoginRequest;
import com.agile_tracker.user_access_ms.dtos.auth.RegisterRequest;
import com.agile_tracker.user_access_ms.exceptions.BadRequestException;
import com.agile_tracker.user_access_ms.exceptions.NotFoundException;
import com.agile_tracker.user_access_ms.exceptions.UnauthorizedException;
import com.agile_tracker.user_access_ms.models.Utilisateur;
import com.agile_tracker.user_access_ms.repositories.UtilisateurRepository;
import com.agile_tracker.user_access_ms.security.AuthenticatedUser;
import com.agile_tracker.user_access_ms.security.JwtService;
import com.agile_tracker.user_access_ms.services.AuthService;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthTokens register(RegisterRequest request) {
        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already in use");
        }
        if (utilisateurRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username already in use");
        }

        Utilisateur utilisateur = Utilisateur.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .username(request.getUsername())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .competences(request.getCompetences())
                .capaciteHebdo(request.getCapaciteHebdo())
            .disponibilite(request.getDisponibilite())
                .build();

        Utilisateur saved = utilisateurRepository.save(utilisateur);
        return buildTokens(saved);
    }

    @Override
    public AuthTokens login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword())
        );

        Utilisateur utilisateur = utilisateurRepository.findByUsername(request.getUsernameOrEmail())
                .or(() -> utilisateurRepository.findByEmail(request.getUsernameOrEmail()))
                .orElseThrow(() -> new NotFoundException("User not found"));

        return buildTokens(utilisateur);
    }

    @Override
    public AuthTokens refresh(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        if (username == null) {
            throw new UnauthorizedException("Invalid refresh token");
        }

        Utilisateur utilisateur = utilisateurRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

        if (!jwtService.isTokenValid(refreshToken, new AuthenticatedUser(utilisateur))) {
            throw new UnauthorizedException("Invalid refresh token");
        }

        if (utilisateur.getToken() == null || !utilisateur.getToken().equals(refreshToken)) {
            throw new UnauthorizedException("Refresh token mismatch");
        }

        if (utilisateur.getTokenExpiration() == null || utilisateur.getTokenExpiration().isBefore(LocalDateTime.now())) {
            throw new UnauthorizedException("Refresh token expired");
        }

        return buildTokens(utilisateur);
    }

    private AuthTokens buildTokens(Utilisateur utilisateur) {
        String accessToken = jwtService.generateAccessToken(utilisateur);
        String refreshToken = jwtService.generateRefreshToken(utilisateur);

        utilisateur.setToken(refreshToken);
        utilisateur.setTokenExpiration(LocalDateTime.now().plus(Duration.ofMillis(jwtService.getRefreshExpirationMs())));
        utilisateurRepository.save(utilisateur);

        return AuthTokens.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresInSeconds(jwtService.getAccessExpirationMs() / 1000)
            .refreshExpiresInSeconds(jwtService.getRefreshExpirationMs() / 1000)
                .build();
    }
}
