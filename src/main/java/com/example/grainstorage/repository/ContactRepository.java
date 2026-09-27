package com.example.grainstorage.repository;

import com.example.grainstorage.entity.Contact;
import com.example.grainstorage.entity.enums.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByContactType(ContactType contactType);
}
