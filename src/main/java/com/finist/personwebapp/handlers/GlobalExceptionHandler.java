package com.finist.personwebapp.handlers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.finist.personwebapp.model.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ValidationErrorResponse> handleJSONMismatchException(Exception e){
        ValidationErrorResponse ver = new ValidationErrorResponse("Invalid data");
        ver.errors.put(Exception.class.getCanonicalName(), e.getMessage());
        return new ResponseEntity<>(ver, HttpStatus.BAD_REQUEST);
    }
}
