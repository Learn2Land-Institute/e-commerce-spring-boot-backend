package com.mm.ecommerce.controller;

import com.mm.ecommerce.dto.ConsumerSignUpDTO;
import com.mm.ecommerce.service.ConsumerSignUpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/consumers")
@Slf4j
public class ConsumerSignUpController {
    @Autowired
    private ConsumerSignUpService customerSignUpService;


    @PostMapping("/signUp/")
    public ResponseEntity<?> signUpConsumer(@Valid @RequestBody ConsumerSignUpDTO customerSignUpDTO, BindingResult result) {
        if (result.hasErrors()) {
            // Return a response with validation errors.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }
        log.info("Inside signUpConsumer method of ConsumerSignUpService.");
        boolean isSignUpSuccessful = customerSignUpService.signUpConsumer(customerSignUpDTO);
        return isSignUpSuccessful ?
                ResponseEntity.ok().body("Congratulations!! You are Successfully SignUp!") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please try again! You are unsuccessfully SignUp.");
    }
}
