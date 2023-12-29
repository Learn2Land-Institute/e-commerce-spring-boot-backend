package com.mm.ecommerce.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userID;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    private String firstName;
    private String lastName;
    private LocalDateTime lastLogin;

}
