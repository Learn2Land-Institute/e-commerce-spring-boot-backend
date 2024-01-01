package com.mm.ecommerce.service;

import com.mm.ecommerce.domain.*;
import com.mm.ecommerce.dto.*;
import com.mm.ecommerce.enums.MerchantStatus;
import com.mm.ecommerce.exception.InvalidInputException;
import com.mm.ecommerce.exception.RecordNotFoundException;
import com.mm.ecommerce.repository.MerchantRepository;
import com.mm.ecommerce.repository.StateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    StateRepository stateRepository;

    @Autowired
    RestTemplate restTemplate;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MerchantRegisterResponse registerMerchant(MerchantRegisterRequest merchantRegisterRequest){
        System.out.println("registerMerchant");
        validateMerchantRegisterRequest(merchantRegisterRequest);

        //create bank info request
        BankInfoRequest bankInfoRequest = createBankInfoRequest(merchantRegisterRequest);

        // Validate bank information and get response
        BankInfoResponse bankInfoResponse = validateBankInformation(bankInfoRequest);
        System.out.println(bankInfoResponse.getAmount());

        // create and save merchant
        Merchant merchant = mapMerchantRegisterRequestToMerchant(merchantRegisterRequest);

        merchantRepository.save(merchant);

        MerchantRegisterResponse merchantRegisterResponse = new MerchantRegisterResponse();
        merchantRegisterResponse.setMsg("success"); //will improve later
        return merchantRegisterResponse;

    }

    private Merchant mapMerchantRegisterRequestToMerchant(MerchantRegisterRequest merchantRegisterRequest) {
        Merchant merchant = new Merchant();
        merchant.setFirstName(merchantRegisterRequest.getFirstName());
        merchant.setLastName(merchantRegisterRequest.getLastName());
        merchant.setEmail(merchantRegisterRequest.getEmail());
        String encryptedPassword = passwordEncoder.encode(merchantRegisterRequest.getPassword());
        merchant.setPassword(encryptedPassword);
        merchant.setBusinessName(merchantRegisterRequest.getBusinessName());
        merchant.setBusinessType(merchantRegisterRequest.getBusinessType());
        merchant.setIndustry(merchantRegisterRequest.getIndustry());

        List<Address> addressList = mapAddressList(merchantRegisterRequest.getAddressList());
        merchant.setAddressList(addressList);

        List<PhoneNumber> phoneNumberList = mapPhoneNumberList(merchantRegisterRequest.getPhoneNumberList());
        merchant.setPhoneNumberList(phoneNumberList);

        merchant.setTaxIdentificationNumber(merchantRegisterRequest.getTaxIdentificationNumber());
        merchant.setBusinessRegistrationNumber(merchantRegisterRequest.getBusinessRegistrationNumber());
        merchant.setMerchantStatus(MerchantStatus.New);

        return merchant;
    }

    private List<Address> mapAddressList(List<AddressDTO> addressRequstList){
        return addressRequstList.stream()
                .map(addressRequest -> mapAddress(addressRequest)).collect(Collectors.toList());
    }

    private Address mapAddress(AddressDTO addressdto){
        Address address = new Address();
        address.setLine1(addressdto.getLine1());
        address.setLine2(addressdto.getLine2());
        address.setCity(addressdto.getCity());
        address.setPostalCode(addressdto.getPostalCode());
        address.setAddressType(addressdto.getAddressType());
        State state = getStateById(addressdto.getStateId());
        address.setState(state);
        return address;
    }

    private State getStateById(Integer stateId){
        return stateRepository.findById(stateId)
                .orElseThrow(() -> new RecordNotFoundException("State not found with id: " + stateId));
    }

    private List<PhoneNumber> mapPhoneNumberList(List<PhoneDTO> phoneDTOList){

        return phoneDTOList.stream()
                .map(phoneDTO -> mapPhoneNumber(phoneDTO)).collect(Collectors.toList());
    }

    private PhoneNumber mapPhoneNumber(PhoneDTO phoneDTO){
        PhoneNumber phone = new PhoneNumber();
        phone.setPhoneNumber(phoneDTO.getPhoneNumber());
        phone.setPhoneNumberType(phoneDTO.getPhoneNumberType());
        phone.setDefaultPhoneNumber(phoneDTO.isDefaultPhoneNumber());
        return phone;
    }

    private BankInfoRequest createBankInfoRequest(MerchantRegisterRequest merchantRegisterRequest){
        BankInfoRequest bankInfoRequest = new BankInfoRequest();
        bankInfoRequest.setCardNumber(merchantRegisterRequest.getCardNumber());
        bankInfoRequest.setCardName(merchantRegisterRequest.getCardName());
        bankInfoRequest.setCvvNumber(merchantRegisterRequest.getCvvNumber());
        bankInfoRequest.setExpireDate(merchantRegisterRequest.getExpireDate());
        return bankInfoRequest;
    }

    private BankInfoResponse validateBankInformation(BankInfoRequest bankInfoRequest){

        try {
            // Set up the request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create the request entity with the JSON body
            HttpEntity<BankInfoRequest> requestEntity = new HttpEntity<>(bankInfoRequest, headers);

            // Make the POST request to the other service
            return restTemplate.postForObject("http://localhost:9001/api/v1/getCardInfo", requestEntity,
                    BankInfoResponse.class
            );
        } catch (HttpClientErrorException ex) {
            System.out.println("error record not found in bank service");
            // Log the exception or handle it based on your requirements
            throw new InvalidInputException("Error validating bank information");
        }

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
