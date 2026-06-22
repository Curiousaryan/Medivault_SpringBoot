package com.atharva.medivault.repository;

import com.atharva.medivault.entity.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {
    List<Allergy> findByMedicalProfileId(Long medicalProfileId);
    Optional<Allergy> findByIdAndMedicalProfileId(Long id, Long medicalProfileId);
    boolean existsByAllergyNameAndMedicalProfileId(String allergyName, Long medicalProfileId);
}
