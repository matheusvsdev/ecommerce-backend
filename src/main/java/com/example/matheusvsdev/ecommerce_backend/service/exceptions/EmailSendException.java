package com.example.matheusvsdev.ecommerce_backend.service.exceptions;

public class EmailSendException extends RuntimeException {

    public EmailSendException(String msg) {
        super(msg);
    }

    public EmailSendException(String message, Throwable cause) {
        super(message, cause);
    }
}
