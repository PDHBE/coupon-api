package com.github.pdhbe.couponapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pdhbe.couponapi.domain.dto.CouponGroupDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Table(name = "coupon_groups")
@Entity
@Getter
@NoArgsConstructor
public class CouponGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String issuer_id;
    private String code;
    private String name;
    @Enumerated(EnumType.STRING)
    private CouponGroupStatus status;
    private int amount;
    private int max_count;
    private int current_count;
    private OffsetDateTime valid_from_dt;
    private OffsetDateTime valid_to_dt;
    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;

    @Builder
    public CouponGroup(String issuer_id, String code, String name, int amount, int max_count, String valid_from_dt, String valid_to_dt) {
        this.valid_from_dt = convertToOffsetDateTime(valid_from_dt);
        this.valid_to_dt = convertToOffsetDateTime(valid_to_dt);

        validateDates();

        this.issuer_id = issuer_id;
        this.code = code;
        this.name = name;
        this.amount = amount;
        this.max_count = max_count;

        this.current_count = 0;
        this.created_at = OffsetDateTime.now();
        this.status = CouponGroupStatus.CREATED;
    }

    public CouponGroup modify(String issuer_id, CouponGroupDto couponGroupDto) {
        if (!this.issuer_id.equals(issuer_id)) {
            throw new NotIssuerException("Cannot Modify Because Not Issuer.");
        }
        if (!this.status.equals(CouponGroupStatus.CREATED)) {
            throw new CannotModifyException("Cannot Modify Because Status Is Not CREATED.");
        }

        this.valid_from_dt = convertToOffsetDateTime(couponGroupDto.getValid_from_dt());
        this.valid_to_dt = convertToOffsetDateTime(couponGroupDto.getValid_to_dt());

        validateDates();

        this.name = couponGroupDto.getName();
        this.amount = couponGroupDto.getAmount();
        this.max_count = couponGroupDto.getMax_count();

        this.updated_at = OffsetDateTime.now();
        return this;
    }

    public CouponGroup publish(String issuer_id) {
        if (!this.issuer_id.equals(issuer_id)) {
            throw new NotIssuerException("Cannot Publish Because Not Issuer.");
        }
        if (!this.status.equals(CouponGroupStatus.CREATED)) {
            throw new CannotPublishException("Cannot Publish Because Status is not CREATED.");
        }
        if (this.valid_to_dt.isBefore(OffsetDateTime.now())) {
            throw new CannotPublishException("Cannot Publish Because Coupon Group Expired.");
        }

        this.status = CouponGroupStatus.PUBLISHED;
        return this;
    }

    public CouponGroup disable(String issuer_id) {
        if (!this.issuer_id.equals(issuer_id)) {
            throw new NotIssuerException("Cannot Disable Because Not Issuer.");
        }
        if (this.status.equals(CouponGroupStatus.DISABLED)) {
            throw new CannotDisableException("Cannot Disable Because Coupon Group is Disabled Already.");
        }

        this.status = CouponGroupStatus.DISABLED;
        return this;
    }

    private OffsetDateTime convertToOffsetDateTime(String str) {
        return OffsetDateTime.from(
                DateTimeFormatter.ISO_DATE_TIME.parse(str)
        );
    }

    private void validateDates() {
        if (this.valid_from_dt.isAfter(this.valid_to_dt)) {
            throw new InvalidDateException("valid_from_dt is after valid_to_dt.");
        }
        if (this.valid_to_dt.isBefore(OffsetDateTime.now())) {
            throw new InvalidDateException("valid_to_dt is before now.");
        }
    }

    @JsonIgnore
    public boolean isPublished() {
        return this.status.equals(CouponGroupStatus.PUBLISHED);
    }

    @JsonIgnore
    public boolean isAllCouponUsed() {
        return this.max_count == this.current_count;
    }

    @JsonIgnore
    public boolean isExpired() {
        return this.valid_to_dt.isBefore(OffsetDateTime.now());
    }
}
