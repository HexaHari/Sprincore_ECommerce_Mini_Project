package com.ecommerce.exception;

public class NoSuchEntityFoundException extends RuntimeException {
    public NoSuchEntityFoundException(String message) {
        super(message);
    }
}
