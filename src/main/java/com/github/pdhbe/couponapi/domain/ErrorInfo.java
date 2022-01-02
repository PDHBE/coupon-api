package com.github.pdhbe.couponapi.domain;

import lombok.Getter;

@Getter
public class ErrorInfo {
    int status;
    String msg;

    public ErrorInfo(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
