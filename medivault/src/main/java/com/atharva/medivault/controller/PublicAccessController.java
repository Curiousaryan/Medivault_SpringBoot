package com.atharva.medivault.controller;

import com.atharva.medivault.dto.response.PublicMedicalResponse;
import com.atharva.medivault.exception.ResourceNotFoundException;
import com.atharva.medivault.repository.UserRepository;
import com.atharva.medivault.service.interfaces.PublicAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PublicAccessController {

    private final PublicAccessService publicAccessService;
    private final UserRepository userRepository;

    @PostMapping("/api/token/generate")
    public ResponseEntity<String> generateToken(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(publicAccessService.generateToken(getUserId(userDetails)));
    }

    @GetMapping("/api/public/{token}")
    public ResponseEntity<PublicMedicalResponse> getPublicMedicalInfo(@PathVariable String token) {
        return ResponseEntity.ok(publicAccessService.getPublicMedicalInfo(token));
    }

    @DeleteMapping("/api/token/revoke")
    public ResponseEntity<String> revokeToken(@AuthenticationPrincipal UserDetails userDetails) {
        publicAccessService.revokeToken(getUserId(userDetails));
        return ResponseEntity.ok("Public access token revoked successfully");
    }

    private Long getUserId(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found")).getId();
    }
}
