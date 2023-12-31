package com.mm.ecommerce.service.impl;

import com.mm.ecommerce.adapter.ConsumerAdapter;
import com.mm.ecommerce.dto.ConsumerSignUpDTO;
import com.mm.ecommerce.repository.ConsumerRepository;
import com.mm.ecommerce.repository.UserRepository;
import com.mm.ecommerce.service.ConsumerSignUpService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class ConsumerSignUpServiceImpl implements ConsumerSignUpService {

    @Autowired
    ConsumerRepository customerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ConsumerAdapter consumerAdapter;


    @Override
    public boolean signUpConsumer(ConsumerSignUpDTO consumerSignUpDTO) {
        log.info("Inside signUpConsumer method of CustomerSignUpServiceImpl.");
        try {
            if(!validationSignUpConsumer(consumerSignUpDTO)){
                return false;
            }
            customerRepository.save(consumerAdapter.toConsumerEntity(consumerSignUpDTO));
            return true;
        } catch (Exception e) {
            // Log the exception
            log.error("An error occurred while doing sing up by consumer.");

            // Rethrow the exception or handle it as needed
            throw new RuntimeException("An error occurred while doing sing up by consumer", e);
        }
    }

    private boolean validationSignUpConsumer(ConsumerSignUpDTO consumerSignUpDTO){
        //check email and confirmed email
        if(!(consumerSignUpDTO.getEmail().equals(consumerSignUpDTO.getConfirmedEmail()))){
            return false;
        }
        //check password and confirmed password
        if(!(consumerSignUpDTO.getPassword().equals(consumerSignUpDTO.getConfirmedPassword()))){
            return false;
        }
        // Check if the email is already registered
        if(userRepository.findByEmail(consumerSignUpDTO.getEmail())) {
            return false; // Email is already taken
        }
        return true;
    }

}
