package com.atharva.medivault.service.impl;

import com.atharva.medivault.dto.request.MedicationRequest;
import com.atharva.medivault.dto.response.MedicationResponse;
import com.atharva.medivault.entity.Medication;
import com.atharva.medivault.entity.MedicalProfile;
import com.atharva.medivault.exception.ResourceNotFoundException;
import com.atharva.medivault.repository.MedicationRepository;
import com.atharva.medivault.repository.MedicalProfileRepository;
import com.atharva.medivault.service.interfaces.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;
    private final MedicalProfileRepository medicalProfileRepository;

    @Override
    public MedicationResponse addMedication(MedicationRequest request, Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));

        Medication medication = Medication.builder()
                .medicineName(request.getMedicineName())
                .dosage(request.getDosage())
                .frequency(request.getFrequency())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .active(request.isActive())
                .medicalProfile(profile)
                .build();

        medicationRepository.save(medication);
        return mapToResponse(medication);
    }

    @Override
    public List<MedicationResponse> getAllMedications(Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        return medicationRepository.findByMedicalProfileId(profile.getId())
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public MedicationResponse getMedicationById(Long medicationId, Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        Medication medication = medicationRepository
                .findByIdAndMedicalProfileId(medicationId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Medication not found with id: " + medicationId));
        return mapToResponse(medication);
    }

    @Override
    public MedicationResponse updateMedication(Long medicationId, MedicationRequest request, Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        Medication medication = medicationRepository
                .findByIdAndMedicalProfileId(medicationId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Medication not found with id: " + medicationId));

        medication.setMedicineName(request.getMedicineName());
        medication.setDosage(request.getDosage());
        medication.setFrequency(request.getFrequency());
        medication.setStartDate(request.getStartDate());
        medication.setEndDate(request.getEndDate());
        medication.setActive(request.isActive());

        medicationRepository.save(medication);
        return mapToResponse(medication);
    }

    @Override
    public void deleteMedication(Long medicationId, Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        Medication medication = medicationRepository
                .findByIdAndMedicalProfileId(medicationId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Medication not found with id: " + medicationId));
        medicationRepository.delete(medication);
    }

    @Override
    public List<MedicationResponse> getActiveMedications(Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        return medicationRepository.findByMedicalProfileIdAndActive(profile.getId(), true)
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    private MedicationResponse mapToResponse(Medication medication) {
        return MedicationResponse.builder()
                .id(medication.getId())
                .medicineName(medication.getMedicineName())
                .dosage(medication.getDosage())
                .frequency(medication.getFrequency())
                .startDate(medication.getStartDate())
                .endDate(medication.getEndDate())
                .active(medication.isActive())
                .build();
    }
}
