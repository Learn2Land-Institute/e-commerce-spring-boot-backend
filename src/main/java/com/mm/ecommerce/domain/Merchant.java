package com.mm.ecommerce.domain;

import com.mm.ecommerce.enums.MerchantStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Merchant extends User{
    @Enumerated(EnumType.STRING)
    private MerchantStatus merchantStatus;
    @OneToMany
    private List<Address> addressList;
    @OneToMany
    private List<PhoneNumber> phoneNumberList;
}
