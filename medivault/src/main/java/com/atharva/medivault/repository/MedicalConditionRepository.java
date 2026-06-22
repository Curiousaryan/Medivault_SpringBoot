package com.atharva.medivault.repository;

import com.atharva.medivault.entity.MedicalCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalConditionRepository extends JpaRepository<MedicalCondition, Long> {
    List<MedicalCondition> findByMedicalProfileId(Long medicalProfileId);
    Optional<MedicalCondition> findByIdAndMedicalProfileId(Long id, Long medicalProfileId);
    // FIX: field renamed to ongoing
    List<MedicalCondition> findByMedicalProfileIdAndOngoing(Long medicalProfileId, boolean ongoing);
}
