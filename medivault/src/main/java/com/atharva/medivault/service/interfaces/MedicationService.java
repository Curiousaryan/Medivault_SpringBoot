package com.atharva.medivault.service.interfaces;

import com.atharva.medivault.dto.request.MedicationRequest;
import com.atharva.medivault.dto.response.MedicationResponse;
import java.util.List;

public interface MedicationService {
    MedicationResponse addMedication(MedicationRequest request, Long userId);
    List<MedicationResponse> getAllMedications(Long userId);
    MedicationResponse getMedicationById(Long medicationId, Long userId);
    MedicationResponse updateMedication(Long medicationId, MedicationRequest request, Long userId);
    void deleteMedication(Long medicationId, Long userId);
    List<MedicationResponse> getActiveMedications(Long userId);
}
