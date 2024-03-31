package com.project.resiRed.ExceptionHandler;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handlerArgumentException(IllegalArgumentException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST); // errores del lado del cliente (400)
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handlerRuntimeException(RuntimeException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_GATEWAY); // errores del lado del servidor (500)
    }
}
