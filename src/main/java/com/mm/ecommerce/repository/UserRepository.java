package com.mm.ecommerce.repository;

import com.mm.ecommerce.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);

    @Query("select count(u.email)>0 from User u where u.email=:email")
    Boolean isValidUserByEmail(@Param("email") String email);
}
