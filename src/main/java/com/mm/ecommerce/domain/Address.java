package com.mm.ecommerce.domain;

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
    @ManyToOne(cascade = CascadeType.ALL)
    private State state;
}
