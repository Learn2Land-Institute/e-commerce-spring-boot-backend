package com.mm.ecommerce.service;

import com.mm.ecommerce.dto.requestDTO.UserReqDTO;
import com.mm.ecommerce.dto.responseDTO.ResponseObj;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseObj getUserLogin(UserReqDTO userReqDTO);
}
