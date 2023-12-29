package com.mm.ecommerce.domain;

import com.mm.ecommerce.enums.AddressType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
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

    @Enumerated
    private AddressType addressType;
}
