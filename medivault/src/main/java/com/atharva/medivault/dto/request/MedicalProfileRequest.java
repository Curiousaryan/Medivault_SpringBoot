package com.atharva.medivault.dto.request;

import com.atharva.medivault.enums.BloodGroup;
import com.atharva.medivault.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MedicalProfileRequest {
    @NotNull(message = "Blood group is required")
    private BloodGroup bloodGroup;
    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be positive")
    @Max(value = 150, message = "Age must be realistic")
    private Integer age;
    @NotNull(message = "Height is required")
    @DecimalMin(value = "0.0", message = "Height must be positive")
    private Double heightCm;
    @NotNull(message = "Weight is required")
    @DecimalMin(value = "0.0", message = "Weight must be positive")
    private Double weightKg;
    @NotNull(message = "Gender is required")
    private Gender gender;
    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
}
