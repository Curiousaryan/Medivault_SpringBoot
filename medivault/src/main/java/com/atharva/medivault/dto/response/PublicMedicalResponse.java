package com.atharva.medivault.dto.response;

import com.atharva.medivault.enums.BloodGroup;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PublicMedicalResponse {
    private String fullName;
    private BloodGroup bloodGroup;
    private List<String> allergies;
    private List<String> emergencyContacts;
    private List<String> medications;
    private List<String> conditions;
}
