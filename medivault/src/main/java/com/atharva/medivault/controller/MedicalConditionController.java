package com.atharva.medivault.controller;

import com.atharva.medivault.dto.request.MedicalConditionRequest;
import com.atharva.medivault.dto.response.MedicalConditionResponse;
import com.atharva.medivault.exception.ResourceNotFoundException;
import com.atharva.medivault.repository.UserRepository;
import com.atharva.medivault.service.interfaces.MedicalConditionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conditions")
@RequiredArgsConstructor
public class MedicalConditionController {

    private final MedicalConditionService medicalConditionService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<MedicalConditionResponse> addCondition(
            @Valid @RequestBody MedicalConditionRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(medicalConditionService.addCondition(request, getUserId(userDetails)));
    }

    @GetMapping
    public ResponseEntity<List<MedicalConditionResponse>> getAllConditions(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(medicalConditionService.getAllConditions(getUserId(userDetails)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalConditionResponse> getConditionById(
            @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(medicalConditionService.getConditionById(id, getUserId(userDetails)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalConditionResponse> updateCondition(
            @PathVariable Long id,
            @Valid @RequestBody MedicalConditionRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(medicalConditionService.updateCondition(id, request, getUserId(userDetails)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCondition(
            @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        medicalConditionService.deleteCondition(id, getUserId(userDetails));
        return ResponseEntity.ok("Medical condition deleted successfully");
    }

    @GetMapping("/ongoing")
    public ResponseEntity<List<MedicalConditionResponse>> getOngoingConditions(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(medicalConditionService.getOngoingConditions(getUserId(userDetails)));
    }

    private Long getUserId(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found")).getId();
    }
}
