package com.mm.ecommerce.service;

import com.mm.ecommerce.domain.User;
import com.mm.ecommerce.dto.requestDTO.UserReqDTO;
import com.mm.ecommerce.dto.responseDTO.UserResDTO;


public interface UserService {
    UserResDTO getUserLogin(UserReqDTO userReqDTO);

    //public void onApplicationEvent(RegistrationCompleteEvent event);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);
}
