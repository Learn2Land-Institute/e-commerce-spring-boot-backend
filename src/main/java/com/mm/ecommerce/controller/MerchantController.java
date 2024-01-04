package com.mm.ecommerce.controller;

import com.mm.ecommerce.config.URLConstants;
import com.mm.ecommerce.dto.MerchantRegisterRequest;
import com.mm.ecommerce.dto.MerchantRegisterResponse;

import com.mm.ecommerce.enums.MerchantFile;
import com.mm.ecommerce.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@CrossOrigin
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    @PostMapping(URLConstants.MERCHANT_ENDPOINTS)
    public ResponseEntity<?> registerMerchant(
            @RequestParam Map<String,MultipartFile> fileParts,
            @RequestPart("merchantData") MerchantRegisterRequest merchantRegisterRequest){
        System.out.println("registerMerchant controller");
        return new ResponseEntity<MerchantRegisterResponse>(merchantService.registerMerchant(merchantRegisterRequest, fileParts), HttpStatus.OK);
    }

}
