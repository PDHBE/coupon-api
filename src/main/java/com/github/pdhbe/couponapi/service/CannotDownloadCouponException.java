package com.github.pdhbe.couponapi.service;

public class CannotDownloadCouponException extends RuntimeException {
    public CannotDownloadCouponException(String message) {
        super(message);
    }
}
