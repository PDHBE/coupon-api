package com.github.pdhbe.couponapi.domain;

public class CannotModifyException extends RuntimeException {
    public CannotModifyException(String message) {
        super(message);
    }
}
