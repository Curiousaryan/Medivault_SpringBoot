package com.atharva.medivault.controller;

import com.atharva.medivault.dto.request.EmergencyContactRequest;
import com.atharva.medivault.dto.response.EmergencyContactResponse;
import com.atharva.medivault.exception.ResourceNotFoundException;
import com.atharva.medivault.repository.UserRepository;
import com.atharva.medivault.service.interfaces.EmergencyContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class EmergencyContactController {

    private final EmergencyContactService emergencyContactService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<EmergencyContactResponse> addContact(
            @Valid @RequestBody EmergencyContactRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(emergencyContactService.addContact(request, getUserId(userDetails)));
    }

    @GetMapping
    public ResponseEntity<List<EmergencyContactResponse>> getAllContacts(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(emergencyContactService.getAllContacts(getUserId(userDetails)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmergencyContactResponse> getContactById(
            @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(emergencyContactService.getContactById(id, getUserId(userDetails)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmergencyContactResponse> updateContact(
            @PathVariable Long id,
            @Valid @RequestBody EmergencyContactRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(emergencyContactService.updateContact(id, request, getUserId(userDetails)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(
            @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        emergencyContactService.deleteContact(id, getUserId(userDetails));
        return ResponseEntity.ok("Emergency contact deleted successfully");
    }

    private Long getUserId(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found")).getId();
    }
}
