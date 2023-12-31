package com.mm.ecommerce.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String code;

    private String name;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Country country;
    @Embedded
    private AuditData auditData;
}
