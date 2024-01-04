package com.mm.ecommerce.service;

import com.mm.ecommerce.domain.*;
import com.mm.ecommerce.dto.*;
import com.mm.ecommerce.enums.MerchantFile;
import com.mm.ecommerce.enums.MerchantStatus;
import com.mm.ecommerce.exception.MerchantException;
import com.mm.ecommerce.repository.MerchantRepository;
import com.mm.ecommerce.repository.StateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Value("${bank-service-url}")
    private String bankServiceUrl;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MerchantRegisterResponse registerMerchant(MerchantRegisterRequest merchantRegisterRequest
            , Map<String,MultipartFile> fileParts){
        System.out.println("registerMerchant service");

        validateMerchantRegisterRequest(merchantRegisterRequest);

        validateMerchantFiles(fileParts);

        //create bank info request
        BankInfoRequest bankInfoRequest = createBankInfoRequest(merchantRegisterRequest);

        // Validate bank information and get response
        BankInfoResponse bankInfoResponse = validateBankInformation(bankInfoRequest);
        System.out.println(bankInfoResponse.getAmount());

        // create and save merchant
        Merchant merchant = mapMerchantRegisterRequestToMerchant(merchantRegisterRequest,fileParts);

        merchantRepository.save(merchant);

        MerchantRegisterResponse merchantRegisterResponse = new MerchantRegisterResponse();
        merchantRegisterResponse.setMsg("success"); //will improve later
        return merchantRegisterResponse;

    }

    private List<File> mapSaveFiles(Map<String, MultipartFile> fileParts) {
        List<File> fileList = new ArrayList<>();

        // Create a Path object for the directory
        Path directory = Paths.get(System.getProperty("user.dir"), this.uploadDirectory);

        try {
            // Create directory if it does not exist
            if (!Files.exists(directory)) {
                Files.createDirectory(directory);
            }

            for (Map.Entry<String, MultipartFile> entry : fileParts.entrySet()) {
                System.out.println("service saveFiles loop");

                String fileName = System.currentTimeMillis() + "_" + entry.getValue().getOriginalFilename();
                File file = new File();

                // Map key to enum
                file.setMerchantFile(MerchantFile.valueOf(entry.getKey().toUpperCase()));

                file.setFileName(fileName);
                file.setFilePath(String.valueOf(directory));
                fileList.add(file);

                // Create a Path object for the destination file
                Path destinationPath = Paths.get(directory.toString(), fileName);

                // Copy file to destination
                Files.copy(entry.getValue().getInputStream(), destinationPath);
            }

            System.out.println("Files are saved successfully in " + directory);
            return fileList;
        } catch (IOException | IllegalArgumentException ex) {
            throw new MerchantException("Error processing files: " + ex.getMessage());
        }
    }

    private Merchant mapMerchantRegisterRequestToMerchant(MerchantRegisterRequest merchantRegisterRequest
            , Map<String,MultipartFile> fileParts) {
        Merchant merchant = new Merchant();
        merchant.setFirstName(merchantRegisterRequest.getFirstName());
        merchant.setLastName(merchantRegisterRequest.getLastName());
        merchant.setEmail(merchantRegisterRequest.getEmail());
        String encryptedPassword = passwordEncoder.encode(merchantRegisterRequest.getPassword());
        merchant.setPassword(encryptedPassword);
        merchant.setBusinessName(merchantRegisterRequest.getBusinessName());
        merchant.setBusinessType(merchantRegisterRequest.getBusinessType());
        merchant.setIndustry(merchantRegisterRequest.getIndustry());

        List<File> fileList = mapSaveFiles(fileParts);

        List<Address> addressList = mapAddressList(merchantRegisterRequest.getAddressList());
        merchant.setAddressList(addressList);

        List<PhoneNumber> phoneNumberList = mapPhoneNumberList(merchantRegisterRequest.getPhoneNumberList());
        merchant.setPhoneNumberList(phoneNumberList);

        merchant.setTaxIdentificationNumber(merchantRegisterRequest.getTaxIdentificationNumber());
        merchant.setBusinessRegistrationNumber(merchantRegisterRequest.getBusinessRegistrationNumber());
        merchant.setMerchantStatus(MerchantStatus.New);
        merchant.setMerchantFileList(fileList);

        return merchant;
    }

    private List<Address> mapAddressList(List<AddressDTO> addressRequstList){
        return addressRequstList.stream()
                .map(this::mapAddress).collect(Collectors.toList());
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
                .orElseThrow(() -> new MerchantException("State not found with id: " + stateId));
    }

    private List<PhoneNumber> mapPhoneNumberList(List<PhoneDTO> phoneDTOList){

        return phoneDTOList.stream()
                .map(this::mapPhoneNumber).collect(Collectors.toList());
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
        if(merchantRegisterRequest.getAddressList() == null || merchantRegisterRequest.getAddressList().isEmpty()){
            throw new MerchantException("Address is required");
        }
        if(merchantRegisterRequest.getPhoneNumberList() == null || merchantRegisterRequest.getPhoneNumberList().isEmpty()){
            throw new MerchantException("PhoneNumber is required");
        }
        if(merchantRegisterRequest.getTaxIdentificationNumber() == null || merchantRegisterRequest.getTaxIdentificationNumber().trim().isEmpty()){
            throw new MerchantException("TaxIdentification is required");
        }
        if(merchantRegisterRequest.getBusinessRegistrationNumber() == null || merchantRegisterRequest.getBusinessRegistrationNumber().trim().isEmpty()){
            throw new MerchantException("Business Registration is required");
        }
    }

    private void validateMerchantFiles(Map<String,MultipartFile> fileParts){
        System.out.println(fileParts.entrySet().size());
        if(fileParts.entrySet().size()!=3){
            throw new MerchantException("files' count must be 3");
        }
        for(Map.Entry<String, MultipartFile> entry : fileParts.entrySet()){
            String key = entry.getKey();
            if(!(key.equalsIgnoreCase(String.valueOf(MerchantFile.BUSINESSDOC)) ||
            key.equalsIgnoreCase(String.valueOf(MerchantFile.DRIVINGLIC)) ||
            key.equalsIgnoreCase(String.valueOf(MerchantFile.PROOFADD)))){
                throw new MerchantException("Upload document "+entry.getKey()+" is wrong");
            }
        }
    }
}
