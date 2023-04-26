package com.tennisPartner.tennisP.common.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleConfict(CustomException ex) {

        ErrorResponse res = new ErrorResponse(ex.getMessage(), ex.getStatus());

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
}
