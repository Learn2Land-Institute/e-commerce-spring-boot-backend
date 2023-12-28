package com.mm.ecommerce.domain;

import com.mm.ecommerce.enums.OrderitemStatus;
import jakarta.persistence.*;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderItemId;
    @ManyToOne
    private Product product;
    private OrderitemStatus orderitemStatus;
}
