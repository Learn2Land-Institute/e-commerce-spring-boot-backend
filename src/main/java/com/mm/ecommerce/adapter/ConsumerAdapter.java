package com.mm.ecommerce.adapter;

import com.mm.ecommerce.domain.Consumer;
import com.mm.ecommerce.dto.ConsumerSignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ConsumerAdapter {
    @Autowired
    PasswordEncoder passwordEncoder;

    public Consumer toConsumerEntity(ConsumerSignUpDTO dto) {

        Consumer consumer= new Consumer();
        consumer.setEmail(dto.getEmail());
        //consumer.setPassword((dto.getPassword()));
        consumer.setPassword((passwordEncoder.encode(dto.getPassword())));
        if(!verifyPassword(dto.getPassword(), consumer.getPassword()))
            return null;
        consumer.setFirstName(dto.getFirstName());
        consumer.setLastName(dto.getLastName());
        consumer.setLastLogin(LocalDateTime.now());
        return consumer;
    }

    // Example of verifying a user's password
    private boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


//    public CustomerSignUpDTO toDTO(Customer entity) {
         // Set other properties based on the entity
       // return dto;
   // }
}
