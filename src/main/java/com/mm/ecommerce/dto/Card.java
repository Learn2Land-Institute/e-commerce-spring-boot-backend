package com.mm.ecommerce.dto;

import lombok.Data;

@Data
public class Card {
    private String cardNumber;
    private String cardName;
    private String cvvNumber;
    private String expireDate;
}
