package com.mm.ecommerce.service;

import com.mm.ecommerce.dto.MerchantRegisterRequest;
import com.mm.ecommerce.dto.MerchantRegisterResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface MerchantService {
    MerchantRegisterResponse registerMerchant(MerchantRegisterRequest merchantRegisterRequest, Map<String,MultipartFile> fileParts);
}
