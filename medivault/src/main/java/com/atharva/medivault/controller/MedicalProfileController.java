package com.atharva.medivault.controller;

import com.atharva.medivault.dto.request.MedicalProfileRequest;
import com.atharva.medivault.dto.response.MedicalProfileResponse;
import com.atharva.medivault.exception.ResourceNotFoundException;
import com.atharva.medivault.repository.UserRepository;
import com.atharva.medivault.service.interfaces.MedicalProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class MedicalProfileController {

    private final MedicalProfileService medicalProfileService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<MedicalProfileResponse> createProfile(
            @Valid @RequestBody MedicalProfileRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(medicalProfileService.createProfile(request, getUserId(userDetails)));
    }

    @GetMapping
    public ResponseEntity<MedicalProfileResponse> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(medicalProfileService.getProfileByUserId(getUserId(userDetails)));
    }

    @PutMapping
    public ResponseEntity<MedicalProfileResponse> updateProfile(
            @Valid @RequestBody MedicalProfileRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(medicalProfileService.updateProfile(request, getUserId(userDetails)));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProfile(@AuthenticationPrincipal UserDetails userDetails) {
        medicalProfileService.deleteProfile(getUserId(userDetails));
        return ResponseEntity.ok("Medical profile deleted successfully");
    }

    private Long getUserId(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found")).getId();
    }
}
