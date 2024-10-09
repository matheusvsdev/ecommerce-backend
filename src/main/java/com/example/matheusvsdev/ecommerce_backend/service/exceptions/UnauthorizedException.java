package com.example.matheusvsdev.ecommerce_backend.service.exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String msg) {
        super(msg);
    }
}
