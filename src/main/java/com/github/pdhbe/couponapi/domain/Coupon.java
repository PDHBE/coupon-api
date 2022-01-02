package com.github.pdhbe.couponapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Table(name = "coupons")
@Entity
@Getter
@NoArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("user_id")
    private String userId;
    private String code;
    private String name;
    private CouponStatus status;
    private int amount;
    private OffsetDateTime valid_from_dt;
    private OffsetDateTime valid_to_dt;
    private OffsetDateTime used_at;
    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;

    public Coupon(String userId, CouponGroup couponGroup) {
        this.userId = userId;
        this.code = couponGroup.getCode();
        this.name = couponGroup.getName();
        this.amount = couponGroup.getAmount();
        this.valid_from_dt = couponGroup.getValid_from_dt();
        this.valid_to_dt = couponGroup.getValid_to_dt();

        this.status = CouponStatus.ISSUED;
        this.created_at = OffsetDateTime.now();
        this.updated_at = OffsetDateTime.now();
    }

    @JsonIgnore
    public boolean isIssued() {
        return this.status.equals(CouponStatus.ISSUED);
    }

    @JsonIgnore
    public boolean isExpired() {
        return this.valid_to_dt.isBefore(OffsetDateTime.now());
    }

    public Coupon use() {
        this.status = CouponStatus.USED;
        this.used_at = OffsetDateTime.now();
        return this;
    }
}
