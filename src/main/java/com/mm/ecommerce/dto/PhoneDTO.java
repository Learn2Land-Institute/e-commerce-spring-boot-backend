package com.mm.ecommerce.dto;

import com.mm.ecommerce.enums.PhoneNumberType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class PhoneDTO {
    private String phoneNumber;
    private PhoneNumberType phoneNumberType;
    private boolean defaultPhoneNumber;
}
