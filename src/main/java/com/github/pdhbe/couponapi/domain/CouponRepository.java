package com.github.pdhbe.couponapi.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon,Long> {
    boolean existsByUserIdAndCode(String user_id, String code);
    Coupon findByUserIdAndCode(String user_id, String code);
    Page<Coupon> findAllByUserId(String user_id, Pageable pageable);
    List<Coupon> findByCode(String code);
}
