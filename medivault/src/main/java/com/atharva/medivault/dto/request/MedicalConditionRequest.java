package com.atharva.medivault.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MedicalConditionRequest {
    @NotBlank(message = "Condition name is required")
    private String conditionName;
    private String description;
    @NotNull(message = "Diagnosed date is required")
    @PastOrPresent(message = "Diagnosed date cannot be in the future")
    private LocalDate diagnosedDate;
    private boolean ongoing;
}
