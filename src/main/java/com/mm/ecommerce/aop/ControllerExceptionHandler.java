package com.mm.ecommerce.aop;

import com.mm.ecommerce.exception.InvalidInputException;
import com.mm.ecommerce.exception.RecordAlreadyExistsException;
import com.mm.ecommerce.exception.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {RecordNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse resourceNotFoundException(RecordNotFoundException ex, WebRequest webRequest){
        ErrorResponse message =
                ErrorResponse.builder(ex,HttpStatus.NOT_FOUND,ex.getMessage()).build();
        return  message;
    }

    @ExceptionHandler(value = {RecordAlreadyExistsException.class, InvalidInputException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorResponse handleRecordAlreadyExistException(Exception e, WebRequest webRequest){
        ErrorResponse message =
                ErrorResponse.builder(e,HttpStatus.BAD_REQUEST,e.getMessage()).build();
        return message;
    }
}
