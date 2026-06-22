package com.atharva.medivault.dto.response;

import com.atharva.medivault.enums.BloodGroup;
import com.atharva.medivault.enums.Gender;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MedicalProfileResponse {
    private Long id;
    private BloodGroup bloodGroup;
    private Integer age;
    private Double heightCm;
    private Double weightKg;
    private Gender gender;
    private LocalDate dateOfBirth;
}
