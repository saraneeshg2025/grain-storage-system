package com.example.grainstorage.dto.request;

import com.example.grainstorage.entity.enums.ContactType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ContactRequest {

    @NotBlank(message = "Contact name is required")
    private String name;

    @NotNull(message = "Contact type is required (FARMER_COOPERATIVE or PDS_DISTRIBUTION_AGENCY)")
    private ContactType contactType;

    private String phone;

    @Email(message = "Email must be a valid email format")
    private String email;

    private String address;

    public ContactRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactType getContactType() {
        return contactType;
    }

    public void setContactType(ContactType contactType) {
        this.contactType = contactType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
