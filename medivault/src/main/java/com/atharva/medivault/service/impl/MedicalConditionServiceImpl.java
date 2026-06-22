package com.atharva.medivault.service.impl;

import com.atharva.medivault.dto.request.MedicalConditionRequest;
import com.atharva.medivault.dto.response.MedicalConditionResponse;
import com.atharva.medivault.entity.MedicalCondition;
import com.atharva.medivault.entity.MedicalProfile;
import com.atharva.medivault.exception.ResourceNotFoundException;
import com.atharva.medivault.repository.MedicalConditionRepository;
import com.atharva.medivault.repository.MedicalProfileRepository;
import com.atharva.medivault.service.interfaces.MedicalConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalConditionServiceImpl implements MedicalConditionService {

    private final MedicalConditionRepository medicalConditionRepository;
    private final MedicalProfileRepository medicalProfileRepository;

    @Override
    public MedicalConditionResponse addCondition(MedicalConditionRequest request, Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));

        MedicalCondition condition = MedicalCondition.builder()
                .conditionName(request.getConditionName())
                .description(request.getDescription())
                .diagnosedDate(request.getDiagnosedDate())
                .ongoing(request.isOngoing())
                .medicalProfile(profile)
                .build();

        medicalConditionRepository.save(condition);
        return mapToResponse(condition);
    }

    @Override
    public List<MedicalConditionResponse> getAllConditions(Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        return medicalConditionRepository.findByMedicalProfileId(profile.getId())
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public MedicalConditionResponse getConditionById(Long conditionId, Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        MedicalCondition condition = medicalConditionRepository
                .findByIdAndMedicalProfileId(conditionId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Medical condition not found with id: " + conditionId));
        return mapToResponse(condition);
    }

    @Override
    public MedicalConditionResponse updateCondition(Long conditionId, MedicalConditionRequest request, Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        MedicalCondition condition = medicalConditionRepository
                .findByIdAndMedicalProfileId(conditionId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Medical condition not found with id: " + conditionId));

        condition.setConditionName(request.getConditionName());
        condition.setDescription(request.getDescription());
        condition.setDiagnosedDate(request.getDiagnosedDate());
        condition.setOngoing(request.isOngoing());

        medicalConditionRepository.save(condition);
        return mapToResponse(condition);
    }

    @Override
    public void deleteCondition(Long conditionId, Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        MedicalCondition condition = medicalConditionRepository
                .findByIdAndMedicalProfileId(conditionId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Medical condition not found with id: " + conditionId));
        medicalConditionRepository.delete(condition);
    }

    @Override
    public List<MedicalConditionResponse> getOngoingConditions(Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        return medicalConditionRepository.findByMedicalProfileIdAndOngoing(profile.getId(), true)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private MedicalConditionResponse mapToResponse(MedicalCondition condition) {
        return MedicalConditionResponse.builder()
                .id(condition.getId())
                .conditionName(condition.getConditionName())
                .description(condition.getDescription())
                .diagnosedDate(condition.getDiagnosedDate())
                .ongoing(condition.isOngoing())
                .build();
    }
}
