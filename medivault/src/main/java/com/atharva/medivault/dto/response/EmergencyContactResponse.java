package com.atharva.medivault.dto.response;

import com.atharva.medivault.enums.RelationshipType;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmergencyContactResponse {
    private Long id;
    private String name;
    private RelationshipType relationship;
    private String phone;
    private String email;
    private boolean primary;
}
