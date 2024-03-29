package com.mm.ecommerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class ProductDetail {
    @Id
    @GeneratedValue(generator = "shared_generator")
    @GenericGenerator(name = "shared_generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "product"))
    private String id;

    private String description;
}
