package com.utknl.pluto.exception;

import com.utknl.pluto.model.error.AuthorizationError;
import com.utknl.pluto.model.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity handleHttpServerErrorException(HttpServerErrorException exp) {
        return new ResponseEntity<>(ErrorResponse.create(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity handleHttpClientErrorException(HttpClientErrorException exp) {
        return new ResponseEntity<>(ErrorResponse.create(new AuthorizationError("Token Expired!")), HttpStatus.UNAUTHORIZED);
    }

}

