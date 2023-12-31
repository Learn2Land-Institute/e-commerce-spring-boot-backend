package com.mm.ecommerce.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name ="MyOrder")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;
    @OneToMany
    private List<OrderItem> orderItemList;
    private LocalDateTime orderDateTime;
    @ManyToOne
    private Consumer consumer;
}
