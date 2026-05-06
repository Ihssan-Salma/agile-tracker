package com.agile_tracker.user_access_ms.controllers;

import com.agile_tracker.user_access_ms.dtos.auth.AuthResponse;
import com.agile_tracker.user_access_ms.dtos.auth.AuthTokens;
import com.agile_tracker.user_access_ms.dtos.auth.LoginRequest;
import com.agile_tracker.user_access_ms.dtos.auth.RegisterRequest;
import com.agile_tracker.user_access_ms.exceptions.UnauthorizedException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import com.agile_tracker.user_access_ms.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;

    @Value("${security.jwt.refresh-cookie-name:refresh_token}")
    private String refreshCookieName;

    @Value("${security.jwt.refresh-cookie-secure:true}")
    private boolean refreshCookieSecure;

    @Value("${security.jwt.refresh-cookie-path:/}")
    private String refreshCookiePath;

    @Value("${security.jwt.refresh-cookie-same-site:Strict}")
    private String refreshCookieSameSite;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthTokens tokens = authService.register(request);
        return buildAuthResponse(tokens);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthTokens tokens = authService.login(request);
        return buildAuthResponse(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(HttpServletRequest request) {
        String refreshToken = extractRefreshToken(request);
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new UnauthorizedException("Missing refresh token");
        }
        AuthTokens tokens = authService.refresh(refreshToken);
        return buildAuthResponse(tokens);
    }

    private ResponseEntity<AuthResponse> buildAuthResponse(AuthTokens tokens) {
        ResponseCookie cookie = ResponseCookie.from(refreshCookieName, tokens.getRefreshToken())
                .httpOnly(true)
                .secure(refreshCookieSecure)
                .path(refreshCookiePath)
                .sameSite(refreshCookieSameSite)
            .maxAge(tokens.getRefreshExpiresInSeconds())
                .build();

        AuthResponse response = AuthResponse.builder()
                .accessToken(tokens.getAccessToken())
                .tokenType("Bearer")
                .expiresInSeconds(tokens.getExpiresInSeconds())
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }

    private String extractRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (refreshCookieName.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
