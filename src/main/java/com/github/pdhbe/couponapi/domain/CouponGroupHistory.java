package com.github.pdhbe.couponapi.domain;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Table(name = "coupon_group_histories")
@Entity
@NoArgsConstructor
public class CouponGroupHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long coupon_group_id;
    private String user_id;
    private String data;
    private OffsetDateTime created_at;

    @Builder
    public CouponGroupHistory(Long coupon_group_id, String user_id, String data) {
        this.coupon_group_id = coupon_group_id;
        this.user_id = user_id;
        this.data = data;

        this.created_at = OffsetDateTime.now();
    }
}
