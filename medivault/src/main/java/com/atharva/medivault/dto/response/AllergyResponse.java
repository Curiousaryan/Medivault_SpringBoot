package com.atharva.medivault.dto.response;

import com.atharva.medivault.enums.Severity;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AllergyResponse {
    private Long id;
    private String allergyName;
    private String reaction;
    private Severity severity;
}
