package com.mm.ecommerce.service;

import com.mm.ecommerce.dto.ConsumerDTO;
import com.mm.ecommerce.dto.ConsumerRequestDTO;
import com.mm.ecommerce.dto.ConsumerResponseDTO;

public interface ConsumerSignUpService {
    boolean signUpConsumer(ConsumerDTO customerSignUpDTO);

    boolean updateConsumerProfile(String userID, ConsumerRequestDTO consumerRequestDTO);

    ConsumerResponseDTO getConsumerProfile(String userID);
}
