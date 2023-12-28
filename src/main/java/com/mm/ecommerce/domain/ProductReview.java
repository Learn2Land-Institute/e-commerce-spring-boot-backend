package com.mm.ecommerce.domain;

import jakarta.persistence.*;

@Entity
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String productReviewId;
    private String reviewComment;
    private Double rate;
    @ManyToOne
    private Consumer consumer;

}
