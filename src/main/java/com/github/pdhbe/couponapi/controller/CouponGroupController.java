package com.github.pdhbe.couponapi.controller;

import com.github.pdhbe.couponapi.domain.CouponGroup;
import com.github.pdhbe.couponapi.domain.dto.CouponGroupDto;
import com.github.pdhbe.couponapi.domain.dto.ResponseBodyDto;
import com.github.pdhbe.couponapi.service.CouponGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping(value = "/coupon-groups", produces = "application/json;charset=UTF-8")
@RequiredArgsConstructor
@Validated
public class CouponGroupController {

    private final CouponGroupService couponGroupService;

    /**
     * 1. 쿠폰 그룹 생성
     */
    @PostMapping("/{code}")
    public ResponseEntity<ResponseBodyDto> createCouponGroup(@RequestHeader("X-USER-ID") String issuer_id, @PathVariable("code") @Pattern(regexp = "\\w{2,50}") String code, @RequestBody @Valid CouponGroupDto couponGroupDto) {
        CouponGroup couponGroup = couponGroupService.create(issuer_id, code, couponGroupDto);
        ResponseBodyDto responseBodyDto = createResponseBodyDto(couponGroup);
        return ResponseEntity.ok(responseBodyDto);
    }

    /**
     * 2. 쿠폰 그룹 수정
     */
    @PutMapping("/{code}")
    public ResponseEntity<ResponseBodyDto> modifyCouponGroup(@RequestHeader("X-USER-ID") String issuer_id, @PathVariable("code") @Pattern(regexp = "\\w{2,50}") String code, @RequestBody CouponGroupDto couponGroupDto) {
        CouponGroup couponGroup = couponGroupService.modify(issuer_id, code, couponGroupDto);
        ResponseBodyDto responseBodyDto = createResponseBodyDto(couponGroup);
        return ResponseEntity.ok(responseBodyDto);
    }

    /**
     * 3. 쿠폰 그룹 발행
     */
    @PostMapping("/{code}/publish")
    public ResponseEntity<ResponseBodyDto> publishCouponGroup(@RequestHeader("X-USER-ID") String issuer_id, @PathVariable("code") @Pattern(regexp = "\\w{2,50}") String code) {
        CouponGroup couponGroup = couponGroupService.publish(issuer_id, code);
        ResponseBodyDto responseBodyDto = createResponseBodyDto(couponGroup);
        return ResponseEntity.ok(responseBodyDto);
    }

    /**
     * 4. 쿠폰 그룹 비활성화
     */
    @PostMapping("/{code}/disable")
    public ResponseEntity<ResponseBodyDto> disableCouponGroup(@RequestHeader("X-USER-ID") String issuer_id, @PathVariable("code") @Pattern(regexp = "\\w{2,50}") String code) {
        CouponGroup couponGroup = couponGroupService.disable(issuer_id, code);
        ResponseBodyDto responseBodyDto = createResponseBodyDto(couponGroup);
        return ResponseEntity.ok(responseBodyDto);
    }

    private ResponseBodyDto createResponseBodyDto(CouponGroup couponGroup) {
        return ResponseBodyDto.builder()
                .success(true)
                .response(couponGroup)
                .error(null)
                .build();
    }
}
