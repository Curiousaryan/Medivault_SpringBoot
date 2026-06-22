package com.atharva.medivault.entity;

import com.atharva.medivault.enums.MedicationFrequency;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "medications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String medicineName;

    private String dosage;

    @Enumerated(EnumType.STRING)
    private MedicationFrequency frequency;

    private LocalDate startDate;

    private LocalDate endDate;

    // FIX: renamed from isActive to active to avoid Lombok boolean builder conflict
    private boolean active;

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
