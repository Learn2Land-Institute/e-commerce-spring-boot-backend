package com.mm.ecommerce.dto;

import com.mm.ecommerce.domain.Address;
import com.mm.ecommerce.domain.PhoneNumber;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class ConsumerDTO {

    private String userID;

    @NotBlank(message = "Password is required")
    private String password;

    private String confirmedPassword;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Email(message = "Invalid confirmed email format")
    private String confirmedEmail;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private List<Address> addressList;

    private List<PhoneNumber> phoneNumberList;

    private String cardNumber;
}
