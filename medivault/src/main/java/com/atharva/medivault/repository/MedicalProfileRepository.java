package com.atharva.medivault.repository;

import com.atharva.medivault.entity.MedicalProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalProfileRepository extends JpaRepository<MedicalProfile, Long> {
    Optional<MedicalProfile> findByUserId(Long userId);
    boolean existsByUserId(Long userId);
}
