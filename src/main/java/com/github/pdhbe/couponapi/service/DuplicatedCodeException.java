package com.github.pdhbe.couponapi.service;

public class DuplicatedCodeException extends RuntimeException {
    public DuplicatedCodeException(String message) {
        super(message);
    }
}
