package com.mm.ecommerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public abstract class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String voucherId;
}
