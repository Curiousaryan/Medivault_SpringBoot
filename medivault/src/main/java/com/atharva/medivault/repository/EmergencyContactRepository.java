package com.atharva.medivault.repository;

import com.atharva.medivault.entity.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Long> {
    List<EmergencyContact> findByMedicalProfileId(Long medicalProfileId);
    Optional<EmergencyContact> findByIdAndMedicalProfileId(Long id, Long medicalProfileId);
    boolean existsByPhoneAndMedicalProfileId(String phone, Long medicalProfileId);
}
