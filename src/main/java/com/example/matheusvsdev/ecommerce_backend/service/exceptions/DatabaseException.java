package com.example.matheusvsdev.ecommerce_backend.service.exceptions;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String msg) {
        super(msg);
    }
}
