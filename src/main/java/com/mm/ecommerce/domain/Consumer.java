package com.mm.ecommerce.domain;

import com.mm.ecommerce.enums.DBStatus;
import com.mm.ecommerce.enums.MerchantStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Consumer extends User{
    @Enumerated(EnumType.STRING)
    private DBStatus consumerStatus = DBStatus.active;
    @OneToMany
    private List<Address> addressList;
    @OneToMany
    private List<PhoneNumber> phoneNumberList;
}
