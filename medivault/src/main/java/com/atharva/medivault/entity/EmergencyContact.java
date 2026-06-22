package com.atharva.medivault.entity;

import com.atharva.medivault.enums.RelationshipType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "emergency_contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmergencyContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RelationshipType relationship;

    @Column(nullable = false)
    private String phone;

    private String email;

    // FIX: renamed from isPrimary to primary to avoid Lombok boolean builder conflict
    @Column(name = "is_primary")
    private boolean primary;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "medical_profile_id", nullable = false)
    private MedicalProfile medicalProfile;
}
