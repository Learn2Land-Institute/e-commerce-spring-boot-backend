package com.mm.ecommerce.service;

import com.mm.ecommerce.dto.MerchantDTO;
import com.mm.ecommerce.dto.MerchantRegisterRequest;

import java.util.List;

public interface MerchantService {
    MerchantDTO registerMerchant(
            MerchantRegisterRequest merchantRegisterRequest);

    List<MerchantDTO> getAllMerchants();
}
