package com.mm.ecommerce.service;

import com.mm.ecommerce.domain.Consumer;
import com.mm.ecommerce.domain.Merchant;
import com.mm.ecommerce.domain.User;
import com.mm.ecommerce.domain.VerificationToken;
import com.mm.ecommerce.dto.requestDTO.UserReqDTO;
import com.mm.ecommerce.dto.responseDTO.UserResDTO;
import com.mm.ecommerce.exception.LoginException;
import com.mm.ecommerce.repository.UserRespository;
import com.mm.ecommerce.repository.VerificationTokenRepository;
import com.mm.ecommerce.security.TokenManager;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@ComponentScan(basePackages = "security")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRespository userRespository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private TokenManager tokenManager;


    @Override
    @Transactional
    public UserResDTO getUserLogin(UserReqDTO userReqDTO) {
        if (userReqDTO.getEmail() == null || userReqDTO.getPassword() == null) {
            throw new LoginException("Please fill up email and password");
        }else{

            User user = userRespository.findByEmail(userReqDTO.getEmail());

            if(user == null){
                throw new LoginException("Invalid login information.");
            }else{
                boolean isPasswordEncoder = passwordEncoder.matches(userReqDTO.getPassword(),user.getPassword());
                if(!isPasswordEncoder){
                    throw new LoginException("Invalid login information.");
                }else{
                    String token = tokenManager.generateJwtToken(user);
                    VerificationToken verificationToken
                            = new VerificationToken(user, token);
                    VerificationToken verificationToken1 = verificationTokenRepository.findByUser(user.getUserID());
                    if(verificationToken1 == null){
                        verificationTokenRepository.save(verificationToken);
                    }else{
                        verificationTokenRepository.updateByUser(verificationToken.getToken(),verificationToken.getExpirationTime(), verificationToken.getUser().getUserID());
                    }

                    System.out.println("Current date time: "+ LocalDateTime.now());
                    System.out.println("Expired date: "+ verificationToken.getExpirationTime());
                    ModelMapper modelMapper = new ModelMapper();
                    UserResDTO userRes = modelMapper.map(user,UserResDTO.class);
                    userRes.setToken(token);
                    if(user instanceof Merchant){
                        userRes.setRoleName("Merchant");
                        return userRes;
                    }
                    else if(user instanceof Consumer){
                        userRes.setRoleName("Consumer");
                        return userRes;
                    }else{
                        userRes.setRoleName("Admin");
                        return userRes;
                    }

                }
            }
        }
    }
    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken
                = new VerificationToken(user, token);

        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken
                = verificationTokenRepository.findByToken(token);

        if (verificationToken == null) {
            return "invalid";
        }

        User user = verificationToken.getUser();
        LocalDateTime cal = LocalDateTime.now();

        if ((verificationToken.getExpirationTime().getMinute()
                - cal.getMinute()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }
        userRespository.save(user);
        return "valid";
    }

}
