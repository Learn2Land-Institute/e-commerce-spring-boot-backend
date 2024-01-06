package com.mm.ecommerce.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    private String accessToken;
    private Boolean tokenActive;
}
