package com.atharva.medivault.dto.response;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MedicalConditionResponse {
    private Long id;
    private String conditionName;
    private String description;
    private LocalDate diagnosedDate;
    private boolean ongoing;
}
