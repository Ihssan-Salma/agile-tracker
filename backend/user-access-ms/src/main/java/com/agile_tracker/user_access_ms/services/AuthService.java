package com.agile_tracker.user_access_ms.services;

import com.agile_tracker.user_access_ms.dtos.auth.AuthTokens;
import com.agile_tracker.user_access_ms.dtos.auth.LoginRequest;
import com.agile_tracker.user_access_ms.dtos.auth.RegisterRequest;

public interface AuthService {

    AuthTokens register(RegisterRequest request);

    AuthTokens login(LoginRequest request);

    AuthTokens refresh(String refreshToken);
}
