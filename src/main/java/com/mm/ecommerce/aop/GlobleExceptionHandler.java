package com.mm.ecommerce.aop;

import com.mm.ecommerce.dto.responseDTO.LoginErrorType;
import com.mm.ecommerce.exception.LoginException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
* RestControllerAdvice can control of catch controller layer.
* */
@RestControllerAdvice
public class GlobleExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleBadRequestException(LoginException exception) {
        return new ResponseEntity<>(new LoginErrorType(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
