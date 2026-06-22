package com.atharva.medivault.repository;

import com.atharva.medivault.entity.PublicAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublicAccessTokenRepository extends JpaRepository<PublicAccessToken, Long> {
    Optional<PublicAccessToken> findByToken(String token);
    // FIX: field renamed to active
    Optional<PublicAccessToken> findByTokenAndActive(String token, boolean active);
    List<PublicAccessToken> findByUserId(Long userId);
    boolean existsByToken(String token);
}
