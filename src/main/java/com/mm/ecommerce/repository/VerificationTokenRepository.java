package com.mm.ecommerce.repository;

import com.mm.ecommerce.domain.VerificationToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);
    @Modifying
    @Transactional
    @Query("UPDATE VerificationToken v SET v.token = :token, v.expirationTime = :expireDateTime WHERE v.user.userID = :userId")
    void updateByUser(String token, LocalDateTime expireDateTime, String userId);
    @Query("Select v from VerificationToken v where v.user.userID = :userId")
    VerificationToken findByUser(String userId);
}
