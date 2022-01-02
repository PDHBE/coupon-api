package com.github.pdhbe.couponapi.service;

import com.github.pdhbe.couponapi.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponGroupRepository couponGroupRepository;
    private final UserRepository userRepository;

    @Transactional
    public Coupon download(String user_id, String code) {
        CouponGroup couponGroup = couponGroupRepository.findByCode(code);
        if(couponGroup == null){
            throw new NotFoundException();
        }
        if(!couponGroup.isPublished()){
            throw new CannotDownloadCouponException("Cannot Download Coupon Because Status Is Not Published.");
        }
        if(couponGroup.isAllCouponUsed()){
            throw new CannotDownloadCouponException("Cannot Download Coupon Because All Coupon Used.");
        }
        if(couponGroup.isExpired()){
            throw new CannotDownloadCouponException("Cannot Download Coupon Because Coupon Group Expired.");
        }
        if(couponRepository.existsByUserIdAndCode(user_id,code)){
            throw new CannotDownloadCouponException("Cannot Download Coupon Because Coupon Already Downloaded.");
        }

        Coupon coupon = new Coupon(user_id, couponGroup);
        return couponRepository.save(coupon);
    }

    @Transactional
    public Coupon use(String user_id, String code) {
        Coupon coupon = couponRepository.findByUserIdAndCode(user_id, code);
        if(isNotIssuer(user_id,code)){
            throw new NotIssuerException("Cannot Use Coupon Because Not Issuer.");
        }
        if(coupon == null){
            throw new NotFoundException();
        }
        if(!coupon.isIssued()){
            throw new CannotUseCouponException("Cannot Use Coupon Because Coupon Is Not Issued.");
        }
        if(coupon.isExpired()){
            throw new CannotUseCouponException("Cannot Use Coupon Because Coupon Expired.");
        }


        coupon.use();
        User user = userRepository.findById(coupon.getUserId()).orElseThrow(NotFoundException::new);
        user.addPoint(coupon);
        return coupon;
    }

    @Transactional(readOnly = true)
    public Page<Coupon> getAllCoupons(String user_id, Pageable pageable) {
        Page<Coupon> couponPage = couponRepository.findAllByUserId(user_id, pageable);
        if(couponPage.isEmpty()){
            throw new NotFoundException();
        }
        return couponPage;
    }

    private boolean isNotIssuer(String user_id, String code) {
        return couponRepository.findByCode(code).stream()
                .map(Coupon::getUserId)
                .noneMatch(userId -> userId.equals(user_id));
    }
}
