package com.mm.ecommerce.controller;

import com.mm.ecommerce.dto.requestDTO.UserReqDTO;
import com.mm.ecommerce.security.TokenManager;
import com.mm.ecommerce.service.JwtUserDetailsService;
import com.mm.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenManager tokenManager;

    @Autowired
    UserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> saveUserLogin(@RequestBody UserReqDTO userReqDTO){
        return new ResponseEntity<>(userService.getUserLogin(userReqDTO), HttpStatus.OK);
    }
    @PostMapping("/hello")
    public ResponseEntity<?> getAuthorization(@RequestBody UserReqDTO userReqDTO){
        return new ResponseEntity<>(userDetailsService.loadUserByUsername(userReqDTO.getEmail()), HttpStatus.OK);
    }


}
