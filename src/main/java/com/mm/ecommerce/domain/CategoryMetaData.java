package com.mm.ecommerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CategoryMetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String categorymetaDataId;
    private String categoryMetaData;
}
