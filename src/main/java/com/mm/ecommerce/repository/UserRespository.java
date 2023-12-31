package com.mm.ecommerce.repository;

import com.mm.ecommerce.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRespository extends JpaRepository<User,String> {
    User findByEmail(String email);
}
