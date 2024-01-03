package com.mm.ecommerce.dto.responseDTO;

public class LoginErrorType {
    private String errorMessage;

    public LoginErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

