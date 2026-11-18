package com.danielgomesott.relaycore.common;

import com.danielgomesott.relaycore.destination.DestinationAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptHandler {


    @ExceptionHandler(DestinationAlreadyExistsException.class)
    public ProblemDetail handleDestinationAlreadyExists(DestinationAlreadyExistsException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

}
