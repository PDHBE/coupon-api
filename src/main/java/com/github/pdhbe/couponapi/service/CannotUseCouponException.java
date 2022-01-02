package com.github.pdhbe.couponapi.service;

public class CannotUseCouponException extends RuntimeException {
    public CannotUseCouponException(String message) {
        super(message);
    }
}
