package com.github.pdhbe.couponapi.service;

import com.github.pdhbe.couponapi.domain.CouponGroup;
import com.github.pdhbe.couponapi.domain.CouponGroupHistory;
import com.github.pdhbe.couponapi.domain.CouponGroupHistoryRepository;
import com.github.pdhbe.couponapi.domain.CouponGroupRepository;
import com.github.pdhbe.couponapi.domain.dto.CouponGroupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponGroupService {

    private final CouponGroupRepository couponGroupRepository;
    private final CouponGroupHistoryRepository couponGroupHistoryRepository;

    @Transactional
    public CouponGroup create(String issuer_id, String code, CouponGroupDto couponGroupDto) {
        if(couponGroupRepository.existsByCode(code)){
            throw new DuplicatedCodeException("Cannot Create Because Duplicated Code.");
        }
        CouponGroup couponGroup = CouponGroup.builder()
                .issuer_id(issuer_id)
                .code(code)
                .name(couponGroupDto.getName())
                .amount(couponGroupDto.getAmount())
                .max_count(couponGroupDto.getMax_count())
                .valid_from_dt(couponGroupDto.getValid_from_dt())
                .valid_to_dt(couponGroupDto.getValid_to_dt())
                .build();

        CouponGroup savedCouponGroup = couponGroupRepository.save(couponGroup);

        CouponGroupHistory couponGroupHistory = createCouponGroupHistory(savedCouponGroup);

        couponGroupHistoryRepository.save(couponGroupHistory);

        return savedCouponGroup;
    }

    @Transactional
    public CouponGroup modify(String issuer_id, String code, CouponGroupDto couponGroupDto) {
        CouponGroup couponGroup = couponGroupRepository.findByCode(code);
        if (couponGroup == null) {
            throw new NotFoundException();
        }

        CouponGroup modifiedCouponGroup = couponGroup.modify(issuer_id, couponGroupDto);

        CouponGroupHistory couponGroupHistory = createCouponGroupHistory(modifiedCouponGroup);

        couponGroupHistoryRepository.save(couponGroupHistory);

        return modifiedCouponGroup;
    }

    @Transactional
    public CouponGroup publish(String issuer_id, String code) {
        CouponGroup couponGroup = couponGroupRepository.findByCode(code);
        if (couponGroup == null) {
            throw new NotFoundException();
        }

        CouponGroup publishedCouponGroup = couponGroup.publish(issuer_id);

        CouponGroupHistory couponGroupHistory = createCouponGroupHistory(publishedCouponGroup);

        couponGroupHistoryRepository.save(couponGroupHistory);

        return publishedCouponGroup;
    }

    @Transactional
    public CouponGroup disable(String issuer_id, String code) {
        CouponGroup couponGroup = couponGroupRepository.findByCode(code);
        if (couponGroup == null) {
            throw new NotFoundException();
        }

        CouponGroup disabledCouponGroup = couponGroup.disable(issuer_id);

        CouponGroupHistory couponGroupHistory = createCouponGroupHistory(disabledCouponGroup);

        couponGroupHistoryRepository.save(couponGroupHistory);

        return disabledCouponGroup;
    }

    private CouponGroupHistory createCouponGroupHistory(CouponGroup couponGroup) {
        return CouponGroupHistory.builder()
                .coupon_group_id(couponGroup.getId())
                .user_id(couponGroup.getIssuer_id())
                .data(couponGroup.getStatus().getValue())
                .build();
    }
}
