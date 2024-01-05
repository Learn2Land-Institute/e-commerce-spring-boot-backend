package com.mm.ecommerce.service;

import com.mm.ecommerce.domain.User;
import com.mm.ecommerce.dto.UserInfoDetails;
import com.mm.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    //private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByEmail(email);
        // Converting userDetail to UserDetails
        if(userOptional.isEmpty())
            throw new UsernameNotFoundException("User not found " + email);

        UserDetails userDetails = new UserInfoDetails(userOptional.get());
        System.out.println(userDetails);
        return userDetails;

        /*return userOptional.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + email));*/
    }

//    public String addUser(UserInfo userInfo) {
//        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
//        repository.save(userInfo);
//        return "User Added Successfully";
//    }


}
