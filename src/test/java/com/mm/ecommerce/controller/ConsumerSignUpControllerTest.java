package com.mm.ecommerce.controller;

import com.mm.ecommerce.dto.ConsumerDTO;
import com.mm.ecommerce.expection.ConsumerSignUpException;
import com.mm.ecommerce.service.ConsumerSignUpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


public class ConsumerSignUpControllerTest {

    @Mock
    private ConsumerSignUpService consumerSignUpService;

    @InjectMocks
    private ConsumerSignUpController consumerSignUpController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUpConsumer_ReturnTrue() {
        ConsumerDTO consumerSignUpDTO = createSampleConsumerSignUpDTO();
        when(consumerSignUpService.signUpConsumer(consumerSignUpDTO)).thenReturn(true);

        ResponseEntity<?> responseEntity = consumerSignUpController.signUpConsumer(consumerSignUpDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Congratulations!! You are Successfully SignUp!", responseEntity.getBody());
        verify(consumerSignUpService, times(1)).signUpConsumer(consumerSignUpDTO);
    }

    @Test
    public void testSignUpConsumer_ReturnFalse() {
        ConsumerDTO consumerSignUpDTO = createSampleConsumerSignUpDTO();
        when(consumerSignUpService.signUpConsumer(consumerSignUpDTO))
                .thenThrow(new ConsumerSignUpException("This Email is already exist."));

        // Use assertThrows to check for the expected exception
        assertThrows(ConsumerSignUpException.class, () -> {
            consumerSignUpController.signUpConsumer(consumerSignUpDTO);
        });
        verify(consumerSignUpService, times(1)).signUpConsumer(consumerSignUpDTO);
    }


    private ConsumerDTO createSampleConsumerSignUpDTO() {
        ConsumerDTO signUpDTO = new ConsumerDTO();
        signUpDTO.setPassword("samplePassword");
        signUpDTO.setConfirmedPassword("samplePassword");
        signUpDTO.setEmail("test@example.com");
        signUpDTO.setConfirmedEmail("test@example.com");
        signUpDTO.setFirstName("John");
        signUpDTO.setLastName("Doe");

        return signUpDTO;
    }

}


