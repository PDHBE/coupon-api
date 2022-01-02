package com.github.pdhbe.couponapi.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponGroupRepository extends JpaRepository<CouponGroup,Long> {
    CouponGroup findByCode(String code);
    boolean existsByCode(String code);
}
