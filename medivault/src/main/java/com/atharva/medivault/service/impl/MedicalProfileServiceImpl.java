package com.atharva.medivault.service.impl;

import com.atharva.medivault.dto.request.MedicalProfileRequest;
import com.atharva.medivault.dto.response.MedicalProfileResponse;
import com.atharva.medivault.entity.MedicalProfile;
import com.atharva.medivault.entity.User;
import com.atharva.medivault.exception.ProfileAlreadyExistsException;
import com.atharva.medivault.exception.ResourceNotFoundException;
import com.atharva.medivault.repository.MedicalProfileRepository;
import com.atharva.medivault.repository.UserRepository;
import com.atharva.medivault.service.interfaces.MedicalProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicalProfileServiceImpl implements MedicalProfileService {

    private final MedicalProfileRepository medicalProfileRepository;
    private final UserRepository userRepository;

    @Override
    public MedicalProfileResponse createProfile(MedicalProfileRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        if (medicalProfileRepository.existsByUserId(userId)) {
            throw new ProfileAlreadyExistsException("Medical profile already exists for this user");
        }

        MedicalProfile profile = MedicalProfile.builder()
                .bloodGroup(request.getBloodGroup())
                .age(request.getAge())
                .heightCm(request.getHeightCm())
                .weightKg(request.getWeightKg())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .user(user)
                .build();

        medicalProfileRepository.save(profile);
        return mapToResponse(profile);
    }

    @Override
    public MedicalProfileResponse getProfileByUserId(Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found for user: " + userId));
        return mapToResponse(profile);
    }

    @Override
    public MedicalProfileResponse updateProfile(MedicalProfileRequest request, Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found for user: " + userId));

        profile.setBloodGroup(request.getBloodGroup());
        profile.setAge(request.getAge());
        profile.setHeightCm(request.getHeightCm());
        profile.setWeightKg(request.getWeightKg());
        profile.setGender(request.getGender());
        profile.setDateOfBirth(request.getDateOfBirth());

        medicalProfileRepository.save(profile);
        return mapToResponse(profile);
    }

    @Override
    public void deleteProfile(Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found for user: " + userId));
        medicalProfileRepository.delete(profile);
    }

    private MedicalProfileResponse mapToResponse(MedicalProfile profile) {
        return MedicalProfileResponse.builder()
                .id(profile.getId())
                .bloodGroup(profile.getBloodGroup())
                .age(profile.getAge())
                .heightCm(profile.getHeightCm())
                .weightKg(profile.getWeightKg())
                .gender(profile.getGender())
                .dateOfBirth(profile.getDateOfBirth())
                .build();
    }
}
