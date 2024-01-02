package com.mm.ecommerce.aop;

import com.mm.ecommerce.dto.CustomErrorType;
import com.mm.ecommerce.expection.ConsumerSignUpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex,
//            HttpHeaders headers,
//            HttpStatus status,
//            WebRequest request) {
//
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//
//        // Customize your response entity here, for example:
//        return super.handleMethodArgumentNotValid(ex, headers, status, request);
//    }

    @ExceptionHandler(ConsumerSignUpException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleBadRequestException(ConsumerSignUpException exception){
        return  new ResponseEntity<>(new CustomErrorType(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

}


