package com.mm.ecommerce.service;

import com.mm.ecommerce.domain.User;
import com.mm.ecommerce.dto.UserInfoDetails;
import com.mm.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> userOptional = userRepository.findByEmail(email);
        // Converting userDetail to UserDetails
        return userOptional.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + email));
    }

    public void saveToken(String email, String token) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        // Converting userDetail to UserDetails
        userOptional.ifPresent(user -> {
            user.setAccessToken(token);
            user.setTokenActive(true);
            userRepository.save(user);
        });
    }

    public Boolean isTokenActive(String token,String email){
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty())
            return false;
        if(userOptional.get().getAccessToken()==null)
            return false;
        return userOptional.get().getAccessToken().equals(token) && userOptional.get().getTokenActive();
    }

    public void deactivateToken(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + email));
        user.setTokenActive(false);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }
}
