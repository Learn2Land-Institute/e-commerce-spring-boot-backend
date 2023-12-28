package com.mm.ecommerce.domain;

import com.mm.ecommerce.enums.PhoneNumberType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String phoneNumberId;
    private String phoneNumber;
    private PhoneNumberType phoneNumberType;
    private boolean defaultPhoneNumber;
}
