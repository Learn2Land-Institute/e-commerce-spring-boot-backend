package com.mm.ecommerce.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
public class VerificationToken {
    //Expired Time at 10 min
    private static final int EXPIRATION_TIME = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String token;
    private LocalDateTime expirationTime;
    @OneToOne
    @JoinColumn(name = "user_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
    private User user;

    public VerificationToken(User user, String token){
        super();
        this.token = token;
        this.user = user;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    public VerificationToken(String token){
        super();
        this.token = token;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }


    private LocalDateTime calculateExpirationDate(int expirationTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime sessionExpirationTime = currentDateTime.plusMinutes(expirationTime);
        return sessionExpirationTime;
    }
}
