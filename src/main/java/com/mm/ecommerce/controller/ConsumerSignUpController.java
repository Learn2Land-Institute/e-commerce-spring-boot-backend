package com.mm.ecommerce.controller;

import com.mm.ecommerce.dto.ConsumerDTO;
import com.mm.ecommerce.dto.ConsumerRequestDTO;
import com.mm.ecommerce.dto.ConsumerResponseDTO;
import com.mm.ecommerce.service.ConsumerSignUpService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/consumers")
@Slf4j
@Validated
public class ConsumerSignUpController {
    @Autowired
    private ConsumerSignUpService customerSignUpService;

    @PostMapping("/signUp")
    @Transactional
    public ResponseEntity<?> signUpConsumer(@Valid @RequestBody ConsumerDTO consumerDTO) {
        log.info("Inside signUpConsumer method of ConsumerSignUpController.");
        boolean isSignUpSuccessful = customerSignUpService.signUpConsumer(consumerDTO);
        return isSignUpSuccessful ?
                ResponseEntity.ok().body("Congratulations!! You are Successfully SignUp!") :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please try again! You are unsuccessfully SignUp.");
    }

    @PutMapping ("/profile/{userID}")
    @Transactional
    public ResponseEntity<?> updateConsumerProfile(@PathVariable String userID, @Valid @RequestBody ConsumerRequestDTO consumerRequestDTO) {
        log.info("Inside updateConsumerProfile method of ConsumerSignUpController.");
        boolean isProfileUpdateSuccessful = customerSignUpService.updateConsumerProfile(userID,consumerRequestDTO);
        return isProfileUpdateSuccessful ?
                ResponseEntity.ok("Congratulations!! Your Profile was successfully updated!"):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please try again! Your Profile was unsuccessfully updated.");
    }
    @GetMapping ("/{userID}")
    @Transactional
    public ResponseEntity<ConsumerResponseDTO> getConsumerProfile(@PathVariable String userID) {
        log.info("Inside getConsumerProfile method of ConsumerSignUpController.");
        ConsumerResponseDTO consumerResponseDTO = customerSignUpService.getConsumerProfile(userID);
        return consumerResponseDTO!=null ?
                ResponseEntity.ok(consumerResponseDTO):
                ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
