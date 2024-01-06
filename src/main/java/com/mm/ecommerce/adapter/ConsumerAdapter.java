package com.mm.ecommerce.adapter;

import com.mm.ecommerce.domain.Address;
import com.mm.ecommerce.domain.Consumer;
import com.mm.ecommerce.domain.PhoneNumber;
import com.mm.ecommerce.dto.ConsumerDTO;
import com.mm.ecommerce.dto.ConsumerResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class ConsumerAdapter {
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;

    public Consumer convertToDomain(ConsumerDTO consumerSignUpDTO) {
        consumerSignUpDTO.setPassword(passwordEncoder.encode(consumerSignUpDTO.getPassword()));
//        Consumer consumer= new Consumer();
//        consumer = modelMapper.map(consumerSignUpDTO, Consumer.class);
//        if(consumerSignUpDTO.getPhoneNumberList()!=null){
//            List<PhoneNumber> phoneNumberList = new ArrayList<>();
//            for(PhoneNumber p :consumerSignUpDTO.getPhoneNumberList()){
//                phoneNumberList.add(p);
//            }
//            consumer.setPhoneNumberList(phoneNumberList);
//        }

        return  modelMapper.map(consumerSignUpDTO, Consumer.class);
    }

    public ConsumerResponseDTO convertToConsumerResponseDTO(Consumer consumer){
  //      ConsumerResponseDTO consumerResponseDTO= modelMapper.map(consumer,ConsumerResponseDTO.class);
//        List<Address> addressList= new ArrayList<>();
//        for(Address a : consumer.getAddressList()){
//            Address address= new Address();
//            address.setAddressId(a.getAddressId());
//            address.setLine1(a.getLine1());
//            address.setLine2(a.getLine2());
//            addressList.add(a);
//        }
//        consumerResponseDTO.setAddressList(addressList);
//        List<PhoneNumber> phoneNumberList= new ArrayList<>();
//        for(PhoneNumber p: consumer.getPhoneNumberList()){
//            phoneNumberList.add(p);
//        }
//        consumerResponseDTO.setPhoneNumberList(phoneNumberList);
        return modelMapper.map(consumer,ConsumerResponseDTO.class);
    }

    public ConsumerDTO convertToConsumerDTO(Consumer consumer){
        return modelMapper.map(consumer,ConsumerDTO.class);
    }

}
