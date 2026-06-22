package com.atharva.medivault.service.interfaces;

import com.atharva.medivault.dto.request.MedicalProfileRequest;
import com.atharva.medivault.dto.response.MedicalProfileResponse;

public interface MedicalProfileService {
    MedicalProfileResponse createProfile(MedicalProfileRequest request, Long userId);
    MedicalProfileResponse getProfileByUserId(Long userId);
    MedicalProfileResponse updateProfile(MedicalProfileRequest request, Long userId);
    void deleteProfile(Long userId);
}
