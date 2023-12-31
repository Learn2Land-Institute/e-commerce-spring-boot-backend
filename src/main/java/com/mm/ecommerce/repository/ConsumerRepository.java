package com.mm.ecommerce.repository;

import com.mm.ecommerce.domain.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRepository extends JpaRepository<Consumer,String> {
}
