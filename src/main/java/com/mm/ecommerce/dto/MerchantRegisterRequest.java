package com.mm.ecommerce.dto;

import com.mm.ecommerce.domain.Address;
import com.mm.ecommerce.domain.PhoneNumber;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class MerchantRegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    // Business Information
    private String businessName;
    private String businessType;
    private String industry;

    //Contact info
    private List<AddressDTO> addressList;
    private List<PhoneDTO> phoneNumberList;

    //Business Identification
    private String taxIdentificationNumber;
    private String businessRegistrationNumber;

    //Bank information
    private String cardNumber;
    private String cardName;
    private String cvvNumber;
    private String expireDate;


}
