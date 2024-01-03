package com.mm.ecommerce.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "app_user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Data
public abstract class User  {
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
