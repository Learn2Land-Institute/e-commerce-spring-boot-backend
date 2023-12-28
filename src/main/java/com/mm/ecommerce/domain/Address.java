package com.mm.ecommerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String addressId;
    private String line1;

    private String line2;

    private String city;

    private String postalCode;
    @ManyToOne
    private State state;
}
