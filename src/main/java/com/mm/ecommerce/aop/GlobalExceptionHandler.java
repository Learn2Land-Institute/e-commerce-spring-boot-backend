package com.mm.ecommerce.aop;

import com.mm.ecommerce.dto.CustomErrorType;
import com.mm.ecommerce.exception.MerchantException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MerchantException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleBadRequestException(MerchantException exception){
        return new ResponseEntity<>(new CustomErrorType(exception.getMessage()),HttpStatus.BAD_REQUEST);
    }
}
