package com.example.matheusvsdev.ecommerce_backend.service.exceptions;

public class ArgumentAlreadyExistsException extends RuntimeException {

    public ArgumentAlreadyExistsException(String msg) {
        super(msg);
    }
}
