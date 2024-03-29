package com.mm.ecommerce.domain;

import com.mm.ecommerce.enums.MerchantStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Merchant extends User{

    // Business Information
    private String businessName;
    private String businessType;
    private String industry;

    // Contact information
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Address> addressList; //business location
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<PhoneNumber> phoneNumberList;

    //private String securityQuestion;
    //private String securityAnswer;

    //Business Identification
    private String taxIdentificationNumber;
    private String businessRegistrationNumber;

    // Approval Status
    @Enumerated(EnumType.STRING)
    private MerchantStatus merchantStatus;

    @Embedded
    private AuditData auditData;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<File> merchantFileList;

}
