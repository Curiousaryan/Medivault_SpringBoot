package com.atharva.medivault.service.impl;

import com.atharva.medivault.dto.request.EmergencyContactRequest;
import com.atharva.medivault.dto.response.EmergencyContactResponse;
import com.atharva.medivault.entity.EmergencyContact;
import com.atharva.medivault.entity.MedicalProfile;
import com.atharva.medivault.exception.ResourceNotFoundException;
import com.atharva.medivault.repository.EmergencyContactRepository;
import com.atharva.medivault.repository.MedicalProfileRepository;
import com.atharva.medivault.service.interfaces.EmergencyContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmergencyContactServiceImpl implements EmergencyContactService {

    private final EmergencyContactRepository emergencyContactRepository;
    private final MedicalProfileRepository medicalProfileRepository;

    @Override
    public EmergencyContactResponse addContact(EmergencyContactRequest request, Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));

        EmergencyContact contact = EmergencyContact.builder()
                .name(request.getName())
                .relationship(request.getRelationship())
                .phone(request.getPhone())
                .email(request.getEmail())
                .primary(request.isPrimary())
                .medicalProfile(profile)
                .build();

        emergencyContactRepository.save(contact);
        return mapToResponse(contact);
    }

    @Override
    public List<EmergencyContactResponse> getAllContacts(Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        return emergencyContactRepository.findByMedicalProfileId(profile.getId())
                .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public EmergencyContactResponse getContactById(Long contactId, Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        EmergencyContact contact = emergencyContactRepository
                .findByIdAndMedicalProfileId(contactId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Emergency contact not found with id: " + contactId));
        return mapToResponse(contact);
    }

    @Override
    public EmergencyContactResponse updateContact(Long contactId, EmergencyContactRequest request, Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        EmergencyContact contact = emergencyContactRepository
                .findByIdAndMedicalProfileId(contactId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Emergency contact not found with id: " + contactId));

        contact.setName(request.getName());
        contact.setRelationship(request.getRelationship());
        contact.setPhone(request.getPhone());
        contact.setEmail(request.getEmail());
        contact.setPrimary(request.isPrimary());

        emergencyContactRepository.save(contact);
        return mapToResponse(contact);
    }

    @Override
    public void deleteContact(Long contactId, Long userId) {
        MedicalProfile profile = medicalProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Medical profile not found"));
        EmergencyContact contact = emergencyContactRepository
                .findByIdAndMedicalProfileId(contactId, profile.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Emergency contact not found with id: " + contactId));
        emergencyContactRepository.delete(contact);
    }

    private EmergencyContactResponse mapToResponse(EmergencyContact contact) {
        return EmergencyContactResponse.builder()
                .id(contact.getId())
                .name(contact.getName())
                .relationship(contact.getRelationship())
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .primary(contact.isPrimary())
                .build();
    }
}
