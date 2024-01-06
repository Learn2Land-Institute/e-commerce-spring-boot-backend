package com.mm.ecommerce.dto;

import com.mm.ecommerce.domain.Address;
import com.mm.ecommerce.domain.PhoneNumber;
import lombok.Data;

import java.util.List;
@Data
public class ConsumerResponseDTO {

    private String userID;

    private String email;

    private String firstName;

    private String lastName;

    private List<Address> addressList;

    private List<PhoneNumber> phoneNumberList;

    private String cardNumber;
}
