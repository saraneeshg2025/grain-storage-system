package com.example.grainstorage.service;

import com.example.grainstorage.dto.request.ContactRequest;
import com.example.grainstorage.entity.Contact;
import com.example.grainstorage.entity.enums.ContactType;
import com.example.grainstorage.exception.ResourceNotFoundException;
import com.example.grainstorage.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional
    public Contact createContact(ContactRequest request) {
        Contact contact = new Contact(
                request.getName(),
                request.getContactType(),
                request.getPhone(),
                request.getEmail(),
                request.getAddress()
        );
        return contactRepository.save(contact);
    }

    public List<Contact> getAllContacts(ContactType contactType) {
        if (contactType != null) {
            return contactRepository.findByContactType(contactType);
        }
        return contactRepository.findAll();
    }

    public Contact getContactById(Long id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with id: " + id));
    }

    @Transactional
    public Contact updateContact(Long id, ContactRequest request) {
        Contact contact = getContactById(id);
        contact.setName(request.getName());
        contact.setContactType(request.getContactType());
        contact.setPhone(request.getPhone());
        contact.setEmail(request.getEmail());
        contact.setAddress(request.getAddress());
        return contactRepository.save(contact);
    }

    @Transactional
    public void deleteContact(Long id) {
        Contact contact = getContactById(id);
        contactRepository.delete(contact);
    }
}
