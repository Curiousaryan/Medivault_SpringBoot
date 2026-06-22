package com.atharva.medivault.service.interfaces;

import com.atharva.medivault.dto.response.PublicMedicalResponse;

public interface PublicAccessService {
    String generateToken(Long userId);
    PublicMedicalResponse getPublicMedicalInfo(String token);
    void revokeToken(Long userId);
}
