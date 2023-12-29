package com.mm.ecommerce.service;

import com.mm.ecommerce.dto.MerchantRegisterRequest;
import com.mm.ecommerce.dto.MerchantRegisterResponse;

public interface MerchantService {
    MerchantRegisterResponse registerMerchant(MerchantRegisterRequest merchantRegisterRequest);
}
