package com.github.pdhbe.couponapi.domain;

public enum CouponGroupStatus {
    CREATED("CREATED"),
    PUBLISHED("PUBLISHED"),
    DISABLED("DISABLED");

    String value;

    CouponGroupStatus(String value) {
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
