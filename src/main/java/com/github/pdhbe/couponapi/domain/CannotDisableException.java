package com.github.pdhbe.couponapi.domain;

public class CannotDisableException extends RuntimeException {
    public CannotDisableException(String message) {
        super(message);
    }
}
