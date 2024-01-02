package com.mm.ecommerce.service.impl;

import com.mm.ecommerce.adapter.ConsumerAdapter;
import com.mm.ecommerce.dto.ConsumerSignUpDTO;
import com.mm.ecommerce.expection.ConsumerSignUpException;
import com.mm.ecommerce.repository.ConsumerRepository;
import com.mm.ecommerce.repository.UserRepository;
import com.mm.ecommerce.service.ConsumerSignUpService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerSignUpServiceImpl implements ConsumerSignUpService {

    @Autowired
    ConsumerRepository customerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ConsumerAdapter consumerAdapter;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    @Override
    public boolean signUpConsumer(ConsumerSignUpDTO consumerSignUpDTO) {
        log.info("Inside signUpConsumer method of CustomerSignUpServiceImpl.");
        validationSignUpConsumer(consumerSignUpDTO);
        customerRepository.save(consumerAdapter.convertToDomain(consumerSignUpDTO));
        return true;
    }

    private void validationSignUpConsumer(ConsumerSignUpDTO consumerSignUpDTO) {
        //check null case for Email
        if (consumerSignUpDTO.getEmail().isEmpty()) {
            throw new ConsumerSignUpException("Email is empty.");
        }
        //check null case for password
        if (consumerSignUpDTO.getPassword().isEmpty()) {
            throw new ConsumerSignUpException("Password is empty.");
        }
        //check email format
        if (!consumerSignUpDTO.getEmail().matches(EMAIL_REGEX)) {
            throw new ConsumerSignUpException("Invalid Email format.");
        }
        //check email and confirmed email
        if (!(consumerSignUpDTO.getEmail().equals(consumerSignUpDTO.getConfirmedEmail()))) {
            throw new ConsumerSignUpException("Email and Confirmed Email must be same.");
        }
        //check password and confirmed password
        if (!(consumerSignUpDTO.getPassword().equals(consumerSignUpDTO.getConfirmedPassword()))) {
            throw new ConsumerSignUpException("Password and Confirmed Password must be same.");
        }
        // Check if the email is already registered
        if (userRepository.findByEmail(consumerSignUpDTO.getEmail())) {
            throw new ConsumerSignUpException("This Email is already exist.");
        }

    }

}
