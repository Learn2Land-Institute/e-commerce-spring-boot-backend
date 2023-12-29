package com.mm.ecommerce.controller;

import com.mm.ecommerce.config.URLConstants;
import com.mm.ecommerce.dto.MerchantRegisterRequest;
import com.mm.ecommerce.dto.MerchantRegisterResponse;

import com.mm.ecommerce.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    @PostMapping(URLConstants.MERCHANT_ENDPOINTS)
    public ResponseEntity<?> registerMerchant(@RequestBody MerchantRegisterRequest merchantRegisterRequest){
        System.out.println("registerMerchant");
       return new ResponseEntity<MerchantRegisterResponse>(merchantService.registerMerchant(merchantRegisterRequest), HttpStatus.OK);
    }

}
