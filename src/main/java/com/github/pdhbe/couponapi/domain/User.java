package com.github.pdhbe.couponapi.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor
public class User {
    @Id
    private String id;
    private String name;
    private int point;
    private OffsetDateTime created_at;
    private OffsetDateTime updated_at;

    public void addPoint(Coupon coupon) {
        this.point += coupon.getAmount();
    }
}
