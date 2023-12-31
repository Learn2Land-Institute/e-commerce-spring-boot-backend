package com.mm.ecommerce.service;

import com.mm.ecommerce.domain.Consumer;
import com.mm.ecommerce.domain.Merchant;
import com.mm.ecommerce.domain.User;
import com.mm.ecommerce.dto.requestDTO.UserReqDTO;
import com.mm.ecommerce.dto.responseDTO.ErrorResDTO;
import com.mm.ecommerce.dto.responseDTO.ResponseObj;
import com.mm.ecommerce.dto.responseDTO.UserResDTO;
import com.mm.ecommerce.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@ComponentScan(basePackages = "security")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseObj getUserLogin(UserReqDTO userReqDTO) {
        try{
            if (userReqDTO.getEmail() == null || userReqDTO.getPassword() == null) {
                ResponseObj response = new ResponseObj();
                ErrorResDTO error = new ErrorResDTO( "Please fill up user email and password",HttpStatus.OK.value()) ;
                response.setError(error);
                return response;
            }
            else{
                User user = userRespository.findByEmail(userReqDTO.getEmail());
                if(user == null){
                    ResponseObj response = new ResponseObj();
                    ErrorResDTO error = new ErrorResDTO("Invalid user name and password.",HttpStatus.OK.value()) ;
                    response.setError(error);
                    return response;
                }
                else {
                    boolean isPasswordCorrect = passwordEncoder.matches(userReqDTO.getPassword(), user.getPassword());
                    if (isPasswordCorrect) {
                        UserResDTO userResDTO = new UserResDTO();
                        if (user instanceof Merchant) {
                            userResDTO.setRoleName("Merchant");
                        } else if (user instanceof Consumer) {
                            userResDTO.setRoleName("Consumer");
                        } else {
                            userResDTO.setRoleName("Admin");
                        }
                        userResDTO.setLastLogin(user.getLastLogin());
                        userResDTO.setFirstName(user.getFirstName());
                        userResDTO.setLastName(user.getLastName());
                        ResponseObj userRes = new ResponseObj ();
                        userRes.setUser(userResDTO);
                        return userRes;
                    } else {
                        ResponseObj response = new ResponseObj();
                        ErrorResDTO error = new ErrorResDTO("Invalid user name and password.", HttpStatus.OK.value()) ;
                        response.setError(error);
                        return response;
                    }
                }
            }
        }catch(Exception ex){
            ResponseObj response = new ResponseObj();
            ErrorResDTO error = new ErrorResDTO("Unexpected error", HttpStatus.CONFLICT.value()) ;
            response.setError(error);
            return response;
        }
    }
}
