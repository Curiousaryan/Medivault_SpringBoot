package com.atharva.medivault.service.interfaces;

import com.atharva.medivault.dto.request.EmergencyContactRequest;
import com.atharva.medivault.dto.response.EmergencyContactResponse;
import java.util.List;

public interface EmergencyContactService {
    EmergencyContactResponse addContact(EmergencyContactRequest request, Long userId);
    List<EmergencyContactResponse> getAllContacts(Long userId);
    EmergencyContactResponse getContactById(Long contactId, Long userId);
    EmergencyContactResponse updateContact(Long contactId, EmergencyContactRequest request, Long userId);
    void deleteContact(Long contactId, Long userId);
}
