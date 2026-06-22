package com.atharva.medivault.dto.request;

import com.atharva.medivault.enums.MedicationFrequency;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MedicationRequest {
    @NotBlank(message = "Medicine name is required")
    private String medicineName;
    @NotBlank(message = "Dosage is required")
    private String dosage;
    @NotNull(message = "Frequency is required")
    private MedicationFrequency frequency;
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
}
