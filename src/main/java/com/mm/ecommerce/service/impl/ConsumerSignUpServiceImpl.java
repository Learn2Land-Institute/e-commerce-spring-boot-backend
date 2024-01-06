package com.mm.ecommerce.service.impl;

import com.mm.ecommerce.adapter.ConsumerAdapter;
import com.mm.ecommerce.domain.Consumer;
import com.mm.ecommerce.dto.Card;
import com.mm.ecommerce.dto.ConsumerDTO;
import com.mm.ecommerce.dto.ConsumerRequestDTO;
import com.mm.ecommerce.dto.ConsumerResponseDTO;
import com.mm.ecommerce.expection.ConsumerSignUpException;
import com.mm.ecommerce.repository.ConsumerRepository;
import com.mm.ecommerce.repository.UserRepository;
import com.mm.ecommerce.service.ConsumerSignUpService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class ConsumerSignUpServiceImpl implements ConsumerSignUpService {

    @Autowired
    private ConsumerRepository consumerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConsumerAdapter consumerAdapter;
    @Autowired
    private RestTemplate restTemplate;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    @Override
    public boolean signUpConsumer(ConsumerDTO consumerSignUpDTO) {
        log.info("Inside signUpConsumer method of CustomerSignUpServiceImpl.");
        validationSignUpConsumer(consumerSignUpDTO);
        consumerRepository.save(consumerAdapter.convertToDomain(consumerSignUpDTO));
        return true;
    }

    @Override
    @Transactional
    public boolean updateConsumerProfile(String userID, ConsumerRequestDTO consumerRequestDTO) {
        log.info("Inside updateConsumerProfile method of CustomerSignUpServiceImpl.");
        consumerRequestDTO.getConsumerDTO().setUserID(userID);
        validationUpdateConsumerProfile(consumerRequestDTO.getConsumerDTO());
        validationSignUpConsumer(consumerRequestDTO.getConsumerDTO());
        //if there have payment information, check payment validation
        if(consumerRequestDTO.getCard() != null){
            Card card=validationForPaymentMethod(consumerRequestDTO.getCard());
            consumerRequestDTO.getConsumerDTO().setCardNumber(card.getCardNumber());
        }
        consumerRepository.save(consumerAdapter.convertToDomain(consumerRequestDTO.getConsumerDTO()));
        return true;
    }

    @Override
    public ConsumerResponseDTO getConsumerProfile(String userID) {
        log.info("Inside getConsumerProfile method of CustomerSignUpServiceImpl.");
        Optional<Consumer> consumerOptional = consumerRepository.findById(userID);
        if (consumerOptional.isPresent()) {
            return consumerAdapter.convertToConsumerResponseDTO(consumerOptional.get());
        } else {
            throw new ConsumerSignUpException("This consumer is not exist.");
        }
    }

    private void validationSignUpConsumer(ConsumerDTO consumerSignUpDTO) {
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
        if (consumerSignUpDTO.getUserID()==null && userRepository.findByEmail(consumerSignUpDTO.getEmail())) {
            throw new ConsumerSignUpException("This Email is already exist.");
        }
        else{
            if(userRepository.findByEmailNotID(consumerSignUpDTO.getEmail(),consumerSignUpDTO.getUserID())){
                throw new ConsumerSignUpException("This Email is already exist.");
            }
        }


    }

    private void validationUpdateConsumerProfile(ConsumerDTO consumerDTO) {
        // Check if this consumer is existed or not
        if (userRepository.findById(consumerDTO.getUserID()).isEmpty()) {
            throw new ConsumerSignUpException("This Consumer is not exist.");
        }
        //check null case for First Name
        if (consumerDTO.getFirstName().isEmpty()) {
            throw new ConsumerSignUpException("First Name is empty.");
        }
        //check null case for Last Name
        if (consumerDTO.getLastName().isEmpty()) {
            throw new ConsumerSignUpException("Last Name is empty.");
        }
        //check at least one address
        if (consumerDTO.getAddressList() == null) {
            throw new ConsumerSignUpException("At least one address is required.");
        }
        //check at least one phone number
        if (consumerDTO.getPhoneNumberList() == null) {
            throw new ConsumerSignUpException("At least one phone number is required.");
        }
    }

    private Card validationForPaymentMethod(Card card) {
        try{
            // Set up the request headers to create JSON body
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Card> cardHttpEntity = new HttpEntity<>(card, httpHeaders);
            return restTemplate.postForObject("http://localhost:9009/api/v1/getCardInfo", cardHttpEntity,Card.class);
        }catch (Exception e){
            throw new ConsumerSignUpException("Payment Information has some issue.");
        }

    }

}
