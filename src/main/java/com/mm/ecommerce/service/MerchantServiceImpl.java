package com.mm.ecommerce.service;

import com.mm.ecommerce.domain.*;
import com.mm.ecommerce.dto.*;
import com.mm.ecommerce.enums.AddressType;
import com.mm.ecommerce.enums.MerchantFileType;
import com.mm.ecommerce.enums.MerchantStatus;
import com.mm.ecommerce.exception.MerchantException;
import com.mm.ecommerce.repository.MerchantRepository;
import com.mm.ecommerce.repository.StateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.file.Paths;
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

    @Value("${bank-service-url}")
    private String bankServiceUrl;

    @Value("${upload_download.directory}")
    private String dir;

    @Value("${prefixdir}")
    private String prefix;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MerchantDTO registerMerchant(
            MerchantRegisterRequest merchantRegisterRequest){
        System.out.println("registerMerchant service");

        validateMerchantRegisterRequest(merchantRegisterRequest);

        //create bank info request
        BankInfoRequest bankInfoRequest = createBankInfoRequest(merchantRegisterRequest);

        // Validate bank information and get response
     //   BankInfoResponse bankInfoResponse = validateBankInformation(bankInfoRequest);
      //  System.out.println(bankInfoResponse.getAmount());

        // create and save merchant
        merchantRegisterRequest.setPassword(passwordEncoder.encode(merchantRegisterRequest.getPassword()));
        merchantRegisterRequest.setMerchantStatus(MerchantStatus.New);
        String path = String.valueOf(Paths.get(System.getProperty(this.prefix), this.dir));
        merchantRegisterRequest.setMerchantFileList(
                merchantRegisterRequest.getMerchantFileList().stream().peek(
                        fileDTO -> fileDTO.setFilePath(path))
                        .collect(Collectors.toList()));

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(merchantRepository.save(modelMapper.map(merchantRegisterRequest,Merchant.class)),MerchantDTO.class);

//        MerchantRegisterResponse merchantRegisterResponse = new MerchantRegisterResponse();
//        merchantRegisterResponse.setMsg("success"); //will improve later
//        return merchantRegisterResponse;

    }

    @Transactional
    public List<MerchantDTO> getAllMerchants(){
        ModelMapper modelMapper = new ModelMapper();
        return merchantRepository.findAll().stream()
                .map(merchant -> modelMapper.map(merchant,MerchantDTO.class))
                .collect(Collectors.toList());
    }

    private State getStateById(Integer stateId){
        return stateRepository.findById(stateId)
                .orElseThrow(() -> new MerchantException("State not found with id: " + stateId));
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
            return restTemplate.postForObject(bankServiceUrl, requestEntity,
                    BankInfoResponse.class
            );
        } catch (ResourceAccessException ex) {
            throw new MerchantException("Error connecting to the bank service. Please try again later. ");
        } catch (HttpClientErrorException ex) {
            System.out.println("error record not found in bank service");
            throw new MerchantException("Error validating bank information");
        }

    }

    private void validateMerchantRegisterRequest(MerchantRegisterRequest merchantRegisterRequest){
        if(merchantRegisterRequest.getFirstName() == null || merchantRegisterRequest.getFirstName().trim().isEmpty()){
            throw new MerchantException("FirstName is required");
        }
        if(merchantRegisterRequest.getLastName() == null || merchantRegisterRequest.getLastName().trim().isEmpty()){
            throw new MerchantException("LastName is required");
        }
        if(merchantRegisterRequest.getEmail() == null || merchantRegisterRequest.getEmail().trim().isEmpty()){
            throw new MerchantException("Email is required");
        }
        if(merchantRegisterRequest.getPassword() == null || merchantRegisterRequest.getPassword().trim().isEmpty()){
            throw new MerchantException("Password is required");
        }
        if(merchantRegisterRequest.getBusinessName() == null || merchantRegisterRequest.getBusinessName().trim().isEmpty()){
            throw new MerchantException("BusinessName is required");
        }
        if(merchantRegisterRequest.getBusinessType() == null || merchantRegisterRequest.getBusinessType().trim().isEmpty()){
            throw new MerchantException("BusinessType is required");
        }
        if(merchantRegisterRequest.getIndustry() == null || merchantRegisterRequest.getIndustry().trim().isEmpty()){
            throw new MerchantException("Industry is required");
        }

        validateMerchantAddress(merchantRegisterRequest);

        if(merchantRegisterRequest.getPhoneNumberList() == null || merchantRegisterRequest.getPhoneNumberList().isEmpty()){
            throw new MerchantException("PhoneNumber is required");
        }
        if(merchantRegisterRequest.getTaxIdentificationNumber() == null || merchantRegisterRequest.getTaxIdentificationNumber().trim().isEmpty()){
            throw new MerchantException("TaxIdentification is required");
        }
        if(merchantRegisterRequest.getBusinessRegistrationNumber() == null || merchantRegisterRequest.getBusinessRegistrationNumber().trim().isEmpty()){
            throw new MerchantException("Business Registration is required");
        }

        validateMerchantFiles(merchantRegisterRequest);
    }

    private void validateMerchantAddress(MerchantRegisterRequest merchantRegisterRequest){
        System.out.println("inside validateMerchantAddress method");
        List<AddressDTO> addressList = merchantRegisterRequest.getAddressList();
        if(addressList == null || addressList.isEmpty()){
            throw new MerchantException("Address is required");
        }
        addressList.forEach(address -> {
            if(address.getAddressType() == null){
                throw new MerchantException("AddressType is required");
            }
            if(!(address.getAddressType().name().equalsIgnoreCase(AddressType.BILLING.name())
                    || address.getAddressType().name().equalsIgnoreCase(AddressType.PHYSICAL.name())
            )){
                throw new MerchantException("AddressType is wrong");
            }
            State state = getStateById(address.getState().getId());
            if(!state.getId().equals(address.getState().getId())){
                throw new MerchantException("StateId is wrong");
            }
        });

    }

    private void validateMerchantFiles(MerchantRegisterRequest merchantRegisterRequest){
        System.out.println("inside validateMerchantFiles method");
        //check files
        List<FileDTO> fileLists = merchantRegisterRequest.getMerchantFileList();
        if(fileLists == null || fileLists.isEmpty()){
            throw new MerchantException("MerchantFiles are required");
        }
        if(fileLists.size() != 3){
            throw new MerchantException("files' count must be 3");
        }
        fileLists.forEach(file-> {
            if(file.getMerchantFileType() == null){
                throw new MerchantException("MerchantFileType is required");
            }
            if(!(file.getMerchantFileType().name().equalsIgnoreCase(MerchantFileType.BUSINESSDOC.name())
                    || file.getMerchantFileType().name().equalsIgnoreCase(MerchantFileType.DRIVINGLIC.name())
                    || file.getMerchantFileType().name().equalsIgnoreCase(MerchantFileType.PROOFADD.name()))){
                throw new MerchantException("Upload document is wrong");
            }

        });
    }


}
