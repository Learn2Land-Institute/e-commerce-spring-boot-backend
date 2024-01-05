package com.mm.ecommerce.dto;

import com.mm.ecommerce.domain.AuditData;
import com.mm.ecommerce.enums.MerchantStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class MerchantDTO {

    private String userID;
    private String email;
    private String password;

    private String firstName;
    private String lastName;
    private LocalDateTime lastLogin;

    // Business Information
    private String businessName;
    private String businessType;
    private String industry;

    // Contact information
    private List<AddressDTO> addressList; //business location
    private List<PhoneDTO> phoneNumberList;

    //Business Identification
    private String taxIdentificationNumber;
    private String businessRegistrationNumber;

    // Approval Status
    private MerchantStatus merchantStatus;

    private AuditData auditData;

    private List<FileDTO> merchantFileList;
}
