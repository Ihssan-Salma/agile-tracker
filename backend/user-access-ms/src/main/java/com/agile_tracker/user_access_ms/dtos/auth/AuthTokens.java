package com.agile_tracker.user_access_ms.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokens {

    private String accessToken;
    private String refreshToken;
    private long expiresInSeconds;
    private long refreshExpiresInSeconds;
}
