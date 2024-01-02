package com.mm.ecommerce.adapter;

import com.mm.ecommerce.domain.Consumer;
import com.mm.ecommerce.dto.ConsumerSignUpDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class ConsumerAdapter {
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    public Consumer convertToDomain(ConsumerSignUpDTO consumerSignUpDTO) {
        consumerSignUpDTO.setPassword(passwordEncoder.encode(consumerSignUpDTO.getPassword()));
        return modelMapper.map(consumerSignUpDTO, Consumer.class);
    }

}
