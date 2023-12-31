package com.mm.ecommerce.controller;

import com.mm.ecommerce.domain.User;
import com.mm.ecommerce.dto.requestDTO.UserReqDTO;
import com.mm.ecommerce.dto.responseDTO.ResponseObj;
import com.mm.ecommerce.dto.responseDTO.UserResDTO;
import com.mm.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public ResponseEntity<?> getUserLogin(@RequestBody UserReqDTO userReqDTO){
        try{
            return ResponseEntity.ok(userService.getUserLogin(userReqDTO));

        }catch (Exception ex){
            return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
