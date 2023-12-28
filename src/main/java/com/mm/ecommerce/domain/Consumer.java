package com.mm.ecommerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Consumer extends User{
    @OneToMany
    private List<Address> addressList;
    @OneToMany
    private List<PhoneNumber> phoneNumberList;
}
