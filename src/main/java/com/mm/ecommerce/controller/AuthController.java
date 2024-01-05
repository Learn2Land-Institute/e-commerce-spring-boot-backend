package com.mm.ecommerce.controller;

import com.mm.ecommerce.dto.AuthReponse;
import com.mm.ecommerce.dto.AuthRequest;
import com.mm.ecommerce.service.AuthService;
import com.mm.ecommerce.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthService authService;

    @GetMapping("/welcome")
    @PreAuthorize("hasAnyAuthority('ROLE_MERCHANT', 'ROLE_SYSTEM_ADMIN')")
    public String welcome() {
        return "Welcome this endpoint is secured and only for merchant and Admin";
    }

    @PostMapping("/generateToken")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {

            String token = jwtService.generateToken(authRequest.getEmail());
            //save token in db
            authService.saveToken(authRequest.getEmail(),token);

            AuthReponse authReponse = AuthReponse.builder()
                    .token(token)
                    .email(authRequest.getEmail())
                    .userRole(authentication.getAuthorities().stream().findFirst().get().getAuthority())
                    .build();


            return new ResponseEntity<>(authReponse, HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
    @PostMapping("/logout")
    public String deactivateToken(@RequestHeader("Authorization") String authorizationHeader) {
        //System.out.println(authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            String email = jwtService.extractUsername(token);
            authService.deactivateToken(email);
        }
        return "Successfully Log out";
    }
}
