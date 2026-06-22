package com.atharva.medivault.service.interfaces;

import com.atharva.medivault.dto.request.LoginRequest;
import com.atharva.medivault.dto.request.RegisterRequest;
import com.atharva.medivault.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
