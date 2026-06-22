package com.atharva.medivault.dto.request;

import com.atharva.medivault.enums.Severity;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AllergyRequest {
    @NotBlank(message = "Allergy name is required")
    private String allergyName;
    private String reaction;
    @NotNull(message = "Severity is required")
    private Severity severity;
}
