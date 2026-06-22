package com.atharva.medivault.repository;

import com.atharva.medivault.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findByMedicalProfileId(Long medicalProfileId);
    Optional<Medication> findByIdAndMedicalProfileId(Long id, Long medicalProfileId);
    // FIX: field renamed to active
    List<Medication> findByMedicalProfileIdAndActive(Long medicalProfileId, boolean active);
}
