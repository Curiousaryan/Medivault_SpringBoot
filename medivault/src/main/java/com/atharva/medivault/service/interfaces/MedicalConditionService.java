package com.atharva.medivault.service.interfaces;

import com.atharva.medivault.dto.request.MedicalConditionRequest;
import com.atharva.medivault.dto.response.MedicalConditionResponse;
import java.util.List;

public interface MedicalConditionService {
    MedicalConditionResponse addCondition(MedicalConditionRequest request, Long userId);
    List<MedicalConditionResponse> getAllConditions(Long userId);
    MedicalConditionResponse getConditionById(Long conditionId, Long userId);
    MedicalConditionResponse updateCondition(Long conditionId, MedicalConditionRequest request, Long userId);
    void deleteCondition(Long conditionId, Long userId);
    List<MedicalConditionResponse> getOngoingConditions(Long userId);
}
