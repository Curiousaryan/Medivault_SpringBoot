package com.atharva.medivault.dto.request;

import com.atharva.medivault.enums.RelationshipType;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EmergencyContactRequest {
    @NotBlank(message = "Contact name is required")
    private String name;
    @NotNull(message = "Relationship is required")
    private RelationshipType relationship;
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String phone;
    @Email(message = "Invalid email format")
    private String email;
    private boolean primary;
}
