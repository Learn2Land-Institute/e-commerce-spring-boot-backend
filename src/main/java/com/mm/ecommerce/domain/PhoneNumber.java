package com.mm.ecommerce.domain;

import com.mm.ecommerce.enums.PhoneNumberType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String phoneNumberId;
    private String phoneNumber;
    @Enumerated
    private PhoneNumberType phoneNumberType;
    private boolean defaultPhoneNumber;
}
