package com.atharva.medivault.service.impl;

import com.atharva.medivault.dto.response.PublicMedicalResponse;
import com.atharva.medivault.entity.MedicalProfile;
import com.atharva.medivault.entity.PublicAccessToken;
import com.atharva.medivault.entity.User;
import com.atharva.medivault.exception.InvalidTokenException;
import com.atharva.medivault.exception.ResourceNotFoundException;
import com.atharva.medivault.repository.MedicalProfileRepository;
import com.atharva.medivault.repository.PublicAccessTokenRepository;
import com.atharva.medivault.repository.UserRepository;
import com.atharva.medivault.service.interfaces.PublicAccessService;
import com.atharva.medivault.util.TokenGenerator;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicAccessServiceImpl implements PublicAccessService {

    private final PublicAccessTokenRepository publicAccessTokenRepository;
    private final MedicalProfileRepository medicalProfileRepository;
    private final UserRepository userRepository;

    @Override
    public String generateToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Revoke all existing active tokens
        publicAccessTokenRepository.findByUserId(userId).forEach(t -> {
            t.setActive(false);
            publicAccessTokenRepository.save(t);
        });

        String tokenValue = TokenGenerator.generateToken();

        PublicAccessToken token = PublicAccessToken.builder()
                .token(tokenValue)
                .active(true)
                .expiresAt(LocalDateTime.now().plusDays(30))
                .user(user)
                .build();

        publicAccessTokenRepository.save(token);
        return tokenValue;
    }

    @Override
    @Transactional(readOnly= true)
    public PublicMedicalResponse getPublicMedicalInfo(String token) {
        PublicAccessToken accessToken = publicAccessTokenRepository
                .findByTokenAndActive(token, true)
                .orElseThrow(() -> new InvalidTokenException("Invalid or inactive token"));

        if (accessToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            accessToken.setActive(false);
            publicAccessTokenRepository.save(accessToken);
            throw new InvalidTokenException("Token has expired");
        }

        MedicalProfile profile = medicalProfileRepository
                .findByUserId(accessToken.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));

        return PublicMedicalResponse.builder()
                .fullName(accessToken.getUser().getFullName())
                .bloodGroup(profile.getBloodGroup())
                .allergies(profile.getAllergies().stream()
                        .map(a -> a.getAllergyName() + " (" + a.getSeverity() + ")")
                        .collect(Collectors.toList()))
                .emergencyContacts(profile.getEmergencyContacts().stream()
                        .map(c -> c.getName() + " - " + c.getPhone())
                        .collect(Collectors.toList()))
                .medications(profile.getMedications().stream()
                        .filter(m -> m.isActive())
                        .map(m -> m.getMedicineName() + " - " + m.getDosage())
                        .collect(Collectors.toList()))
                .conditions(profile.getMedicalConditions().stream()
                        .filter(c -> c.isOngoing())
                        .map(c -> c.getConditionName())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public void revokeToken(Long userId) {
        publicAccessTokenRepository.findByUserId(userId).forEach(t -> {
            t.setActive(false);
            publicAccessTokenRepository.save(t);
        });
    }
}
