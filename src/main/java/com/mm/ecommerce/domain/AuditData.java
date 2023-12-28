package com.mm.ecommerce.domain;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class AuditData {

    private String createdBy;

    private String updatedBy;
    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

}