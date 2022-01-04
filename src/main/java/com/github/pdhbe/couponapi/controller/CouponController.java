package com.github.pdhbe.couponapi.controller;

import com.github.pdhbe.couponapi.domain.Coupon;
import com.github.pdhbe.couponapi.domain.dto.ResponseBodyDto;
import com.github.pdhbe.couponapi.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
@Validated
public class CouponController {

    private final CouponService couponService;

    /**
     * 5. 쿠폰 다운로드
     */
    @PostMapping("/{code}/download")
    public ResponseEntity<ResponseBodyDto> downloadCoupon(@RequestHeader("X-USER-ID") String user_id, @PathVariable("code") @Pattern(regexp = "\\w{2,50}") String code) {
        Coupon coupon = couponService.download(user_id, code);
        ResponseBodyDto responseBodyDto = createResponseBodyDto(coupon);
        return ResponseEntity.ok(responseBodyDto);
    }

    /**
     * 6. 쿠폰 사용
     */
    @PostMapping("/{code}/use")
    public ResponseEntity<ResponseBodyDto> useCoupon(@RequestHeader("X-USER-ID") String user_id, @PathVariable("code") @Pattern(regexp = "\\w{2,50}") String code) {
        Coupon coupon = couponService.use(user_id, code);
        ResponseBodyDto responseBodyDto = createResponseBodyDto(coupon);
        return ResponseEntity.ok(responseBodyDto);
    }

    /**
     * 7. 쿠폰 목록 조회
     */
    @GetMapping
    public ResponseEntity<ResponseBodyDto> getAllCoupons(@RequestHeader("X-USER-ID") String user_id, @PageableDefault(page = 0, size = 5) Pageable pageable) {
        Page<Coupon> couponPage = couponService.getAllCoupons(user_id, pageable);
        ResponseBodyDto responseBodyDto = createResponseBodyDto(couponPage.getContent());
        return ResponseEntity.ok(responseBodyDto);
    }

    private ResponseBodyDto createResponseBodyDto(Coupon coupon) {
        return ResponseBodyDto.builder()
                .success(true)
                .response(coupon)
                .error(null)
                .build();
    }

    private ResponseBodyDto createResponseBodyDto(List<Coupon> couponList) {
        return ResponseBodyDto.builder()
                .success(true)
                .response(couponList)
                .error(null)
                .build();
    }
}
