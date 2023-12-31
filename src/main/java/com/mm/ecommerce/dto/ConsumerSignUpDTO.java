package com.mm.ecommerce.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class ConsumerSignUpDTO {

    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Confirmed password is required")
    private String confirmedPassword;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;
    @Email(message = "Invalid confirmed email format")
    @NotBlank(message = "Confirmed email is required")
    private String confirmedEmail;
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;

    private LocalDateTime lastLogin;
}
