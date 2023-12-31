package com.mm.ecommerce.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Country {
    @Id
    private String code;

    private String name;

    @Embedded
    private AuditData auditData;
}
