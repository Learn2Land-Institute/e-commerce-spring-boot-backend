package com.mm.ecommerce.dto.responseDTO;

public class ErrorResDTO extends Throwable {
    private String message;
    private int statusCode;

    public ErrorResDTO (String message, int statusCode){
        this.message = message;
        this.statusCode = statusCode;
    }
}
