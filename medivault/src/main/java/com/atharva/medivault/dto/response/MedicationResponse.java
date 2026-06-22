package com.atharva.medivault.dto.response;

import com.atharva.medivault.enums.MedicationFrequency;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MedicationResponse {
    private Long id;
    private String medicineName;
    private String dosage;
    private MedicationFrequency frequency;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
}
