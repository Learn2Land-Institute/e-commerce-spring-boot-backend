package com.mm.ecommerce.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userID;
    private String email;
    private String password;

    private String firstName;
    private String lastName;
    private LocalDateTime lastLogin;

}
