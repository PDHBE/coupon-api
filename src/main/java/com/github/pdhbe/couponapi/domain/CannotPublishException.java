package com.github.pdhbe.couponapi.domain;

public class CannotPublishException extends RuntimeException {
    public CannotPublishException(String message) {
        super(message);
    }
}
