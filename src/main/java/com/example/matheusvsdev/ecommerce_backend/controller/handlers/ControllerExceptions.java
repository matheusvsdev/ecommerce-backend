package com.example.matheusvsdev.ecommerce_backend.controller.handlers;

import com.example.matheusvsdev.ecommerce_backend.dto.CustomError;
import com.example.matheusvsdev.ecommerce_backend.dto.ValidationError;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.*;
import com.example.matheusvsdev.ecommerce_backend.service.exceptions.IllegalArgumentException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptions {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> methodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus http = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationError error = new ValidationError(Instant.now()
        ,http.value()
        ,"Dados inválidos"
        ,request.getRequestURI());

        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            error.addError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(http).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError error = new CustomError(Instant.now()
                , status.value()
                , e.getMessage()
                , request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ArgumentAlreadyExistsException.class)
    public ResponseEntity<CustomError> resourceNotFound(ArgumentAlreadyExistsException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        CustomError error = new CustomError(Instant.now()
                , status.value()
                , e.getMessage()
                , request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomError> handleIllegalArgument(IllegalArgumentException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> handleIllegalArgument(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        CustomError error = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleInvalidJson(HttpMessageNotReadableException ex) {
        String errorMessage = "O corpo da requisição contém um JSON malformado. Verifique a sintaxe.";
        Map<String, String> error = new HashMap<>();
        error.put("error", errorMessage);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
