package com.atharva.medivault.service.impl;

import com.atharva.medivault.dto.request.AllergyRequest;
import com.atharva.medivault.dto.response.AllergyResponse;
import com.atharva.medivault.entity.Allergy;
import com.atharva.medivault.entity.MedicalProfile;
import com.atharva.medivault.exception.ResourceNotFoundException;
import com.atharva.medivault.repository.AllergyRepository;
import com.atharva.medivault.repository.MedicalProfileRepository;
import com.atharva.medivault.service.interfaces.AllergyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AllergyServiceImpl implements AllergyService {

    private final AllergyRepository allergyRepository;
    private final MedicalProfileRepository medicalProfileRepository;

    @Override
    public AllergyResponse addAllergy(AllergyRequest request, Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));

        Allergy allergy = Allergy.builder()
                .allergyName(request.getAllergyName())
                .reaction(request.getReaction())
                .severity(request.getSeverity())
                .medicalProfile(profile)
                .build();

        allergyRepository.save(allergy);
        return mapToResponse(allergy);
    }

    @Override
    public List<AllergyResponse> getAllAllergies(Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        return allergyRepository.findByMedicalProfileId(profile.getId())
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public AllergyResponse getAllergyById(Long allergyId, Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        Allergy allergy = allergyRepository
                .findByIdAndMedicalProfileId(allergyId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Allergy not found with id: " + allergyId));
        return mapToResponse(allergy);
    }

    @Override
    public AllergyResponse updateAllergy(Long allergyId, AllergyRequest request, Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        Allergy allergy = allergyRepository
                .findByIdAndMedicalProfileId(allergyId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Allergy not found with id: " + allergyId));

        allergy.setAllergyName(request.getAllergyName());
        allergy.setReaction(request.getReaction());
        allergy.setSeverity(request.getSeverity());

        allergyRepository.save(allergy);
        return mapToResponse(allergy);
    }

    @Override
    public void deleteAllergy(Long allergyId, Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        Allergy allergy = allergyRepository
                .findByIdAndMedicalProfileId(allergyId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Allergy not found with id: " + allergyId));
        allergyRepository.delete(allergy);
    }

    private AllergyResponse mapToResponse(Allergy allergy) {
        return AllergyResponse.builder()
                .id(allergy.getId())
                .allergyName(allergy.getAllergyName())
                .reaction(allergy.getReaction())
                .severity(allergy.getSeverity())
                .build();
    }
}
