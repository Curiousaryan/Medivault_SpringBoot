package com.atharva.medivault.service.interfaces;

import com.atharva.medivault.dto.request.AllergyRequest;
import com.atharva.medivault.dto.response.AllergyResponse;
import java.util.List;

public interface AllergyService {
    AllergyResponse addAllergy(AllergyRequest request, Long userId);
    List<AllergyResponse> getAllAllergies(Long userId);
    AllergyResponse getAllergyById(Long allergyId, Long userId);
    AllergyResponse updateAllergy(Long allergyId, AllergyRequest request, Long userId);
    void deleteAllergy(Long allergyId, Long userId);
}
