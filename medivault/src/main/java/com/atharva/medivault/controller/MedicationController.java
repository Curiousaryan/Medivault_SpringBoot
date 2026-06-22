package com.atharva.medivault.controller;

import com.atharva.medivault.dto.request.MedicationRequest;
import com.atharva.medivault.dto.response.MedicationResponse;
import com.atharva.medivault.exception.ResourceNotFoundException;
import com.atharva.medivault.repository.UserRepository;
import com.atharva.medivault.service.interfaces.MedicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medications")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<MedicationResponse> addMedication(
            @Valid @RequestBody MedicationRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(medicationService.addMedication(request, getUserId(userDetails)));
    }

    @GetMapping
    public ResponseEntity<List<MedicationResponse>> getAllMedications(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(medicationService.getAllMedications(getUserId(userDetails)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationResponse> getMedicationById(
            @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(medicationService.getMedicationById(id, getUserId(userDetails)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicationResponse> updateMedication(
            @PathVariable Long id,
            @Valid @RequestBody MedicationRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(medicationService.updateMedication(id, request, getUserId(userDetails)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedication(
            @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        medicationService.deleteMedication(id, getUserId(userDetails));
        return ResponseEntity.ok("Medication deleted successfully");
    }

    @GetMapping("/active")
    public ResponseEntity<List<MedicationResponse>> getActiveMedications(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(medicationService.getActiveMedications(getUserId(userDetails)));
    }

    private Long getUserId(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found")).getId();
    }
}
