package com.atharva.medivault.controller;

import com.atharva.medivault.dto.request.AllergyRequest;
import com.atharva.medivault.dto.response.AllergyResponse;
import com.atharva.medivault.exception.ResourceNotFoundException;
import com.atharva.medivault.repository.UserRepository;
import com.atharva.medivault.service.interfaces.AllergyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/allergies")
@RequiredArgsConstructor
public class AllergyController {

    private final AllergyService allergyService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<AllergyResponse> addAllergy(
            @Valid @RequestBody AllergyRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(allergyService.addAllergy(request, getUserId(userDetails)));
    }

    @GetMapping
    public ResponseEntity<List<AllergyResponse>> getAllAllergies(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(allergyService.getAllAllergies(getUserId(userDetails)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AllergyResponse> getAllergyById(
            @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(allergyService.getAllergyById(id, getUserId(userDetails)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AllergyResponse> updateAllergy(
            @PathVariable Long id,
            @Valid @RequestBody AllergyRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(allergyService.updateAllergy(id, request, getUserId(userDetails)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAllergy(
            @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        allergyService.deleteAllergy(id, getUserId(userDetails));
        return ResponseEntity.ok("Allergy deleted successfully");
    }

    private Long getUserId(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found")).getId();
    }
}
