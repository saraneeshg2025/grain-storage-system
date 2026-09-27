package com.example.grainstorage.controller;

import com.example.grainstorage.dto.request.ContactRequest;
import com.example.grainstorage.dto.response.ApiResponse;
import com.example.grainstorage.entity.Contact;
import com.example.grainstorage.entity.enums.ContactType;
import com.example.grainstorage.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Contact>> createContact(@Valid @RequestBody ContactRequest request) {
        Contact contact = contactService.createContact(request);
        return new ResponseEntity<>(ApiResponse.ok("Contact registered successfully", contact), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Contact>>> getAllContacts(@RequestParam(required = false) ContactType contactType) {
        List<Contact> contacts = contactService.getAllContacts(contactType);
        return ResponseEntity.ok(ApiResponse.ok(contacts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Contact>> getContactById(@PathVariable Long id) {
        Contact contact = contactService.getContactById(id);
        return ResponseEntity.ok(ApiResponse.ok(contact));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Contact>> updateContact(@PathVariable Long id, @Valid @RequestBody ContactRequest request) {
        Contact updated = contactService.updateContact(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Contact updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.ok(ApiResponse.ok("Contact deleted successfully", null));
    }
}
