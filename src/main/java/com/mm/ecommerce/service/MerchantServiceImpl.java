package com.mm.ecommerce.service;

import com.mm.ecommerce.domain.*;
import com.mm.ecommerce.dto.MerchantRegisterRequest;
import com.mm.ecommerce.dto.MerchantRegisterResponse;
import com.mm.ecommerce.exception.InvalidInputException;
import com.mm.ecommerce.exception.RecordNotFoundException;
import com.mm.ecommerce.repository.MerchantRepository;
import com.mm.ecommerce.repository.StateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    StateRepository stateRepository;

    private final PasswordEncoder passwordEncoder;
    @Transactional
    public MerchantRegisterResponse registerMerchant(MerchantRegisterRequest merchantRegisterRequest){
        System.out.println("registerMerchant");
        validateMerchantRegisterRequest(merchantRegisterRequest);
        MerchantRegisterResponse merchantRegisterResponse = new MerchantRegisterResponse();
        Merchant merchant = new Merchant();
        merchant.setFirstName(merchantRegisterRequest.getFirstName());
        merchant.setLastName(merchantRegisterRequest.getLastName());
        merchant.setEmail(merchantRegisterRequest.getEmail());
        String encryptedPassword = passwordEncoder.encode(merchantRegisterRequest.getPassword());
        merchant.setPassword(encryptedPassword);
        merchant.setBusinessName(merchantRegisterRequest.getBusinessName());
        merchant.setBusinessType(merchantRegisterRequest.getBusinessType());
        merchant.setIndustry(merchantRegisterRequest.getIndustry());
        List<Address> addressList = new ArrayList<>();
        merchantRegisterRequest.getAddressList().forEach(address -> {
            Address add = new Address();
            add.setLine1(address.getLine1());
            add.setLine2(address.getLine2());
            add.setCity(address.getCity());
            add.setPostalCode(address.getPostalCode());
            add.setAddressType(address.getAddressType());
            State state = stateRepository.findById(address.getStateId())
                    .orElseThrow(() -> new RecordNotFoundException("State not found with id: " + address.getStateId()));
            add.setState(state);

            addressList.add(add);
        });

        merchant.setAddressList(addressList);
        List<PhoneNumber> phoneNumberList = new ArrayList<>();
        if(!merchantRegisterRequest.getPhoneNumberList().isEmpty()){
            merchantRegisterRequest.getPhoneNumberList().forEach(phoneNumber -> {
                PhoneNumber phone = new PhoneNumber();
                phone.setPhoneNumber(phoneNumber.getPhoneNumber());
                phone.setPhoneNumberType(phoneNumber.getPhoneNumberType());
                phone.setDefaultPhoneNumber(phoneNumber.isDefaultPhoneNumber());
                phoneNumberList.add(phone);
            });
        }
        merchant.setPhoneNumberList(phoneNumberList);
        merchant.setTaxIdentificationNumber(merchantRegisterRequest.getTaxIdentificationNumber());
        merchant.setBusinessRegistrationNumber(merchantRegisterRequest.getBusinessRegistrationNumber());

        merchantRepository.save(merchant);
        merchantRegisterResponse.setMsg("success"); //will improve later
        return merchantRegisterResponse;

    }
    private void validateMerchantRegisterRequest(MerchantRegisterRequest merchantRegisterRequest){
        if(merchantRegisterRequest.getFirstName() == null || merchantRegisterRequest.getFirstName().trim().isEmpty()){
            throw new InvalidInputException("FirstName is required");
        }
        if(merchantRegisterRequest.getLastName() == null || merchantRegisterRequest.getLastName().trim().isEmpty()){
            throw new InvalidInputException("LastName is required");
        }
        if(merchantRegisterRequest.getEmail() == null || merchantRegisterRequest.getEmail().trim().isEmpty()){
            throw new InvalidInputException("Email is required");
        }
        if(merchantRegisterRequest.getPassword() == null || merchantRegisterRequest.getPassword().trim().isEmpty()){
            throw new InvalidInputException("Password is required");
        }
        if(merchantRegisterRequest.getBusinessName() == null || merchantRegisterRequest.getBusinessName().trim().isEmpty()){
            throw new InvalidInputException("BusinessName is required");
        }
        if(merchantRegisterRequest.getBusinessType() == null || merchantRegisterRequest.getBusinessType().trim().isEmpty()){
            throw new InvalidInputException("BusinessType is required");
        }
        if(merchantRegisterRequest.getIndustry() == null || merchantRegisterRequest.getIndustry().trim().isEmpty()){
            throw new InvalidInputException("Industry is required");
        }
        if(merchantRegisterRequest.getAddressList() == null || merchantRegisterRequest.getAddressList().isEmpty()){
            throw new InvalidInputException("Address is required");
        }
        if(merchantRegisterRequest.getPhoneNumberList() == null || merchantRegisterRequest.getPhoneNumberList().isEmpty()){
            throw new InvalidInputException("PhoneNumber is required");
        }
        if(merchantRegisterRequest.getTaxIdentificationNumber() == null || merchantRegisterRequest.getTaxIdentificationNumber().trim().isEmpty()){
            throw new InvalidInputException("TaxIdentification is required");
        }
        if(merchantRegisterRequest.getBusinessRegistrationNumber() == null || merchantRegisterRequest.getBusinessRegistrationNumber().trim().isEmpty()){
            throw new InvalidInputException("Business Registration is required");
        }
    }
}
