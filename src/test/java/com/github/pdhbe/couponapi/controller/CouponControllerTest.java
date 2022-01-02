package com.github.pdhbe.couponapi.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.is;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class CouponControllerTest extends BaseTest {

  public CouponControllerTest() {
    super("/coupons");
  }

  @Nested
  @DisplayName("7. 보유 쿠폰 목록 조회")
  class GetAllCoupons {
    @Test
    @DisplayName("성공")
    void successDefault() throws Exception {
      success(reqGet("/", "user3"))
              .andExpect(jsonPath("$.response").isArray())
              .andExpect(jsonPath("$.response.length()", is(5)))
              .andExpect(jsonPath("$.response[0].id", is(1)))
              .andExpect(jsonPath("$.response[0].user_id", is("user3")))
              .andDo(print())
      ;
    }

    @Test
    @DisplayName("실패 - 권한 없음 (w/o X-USER-ID)")
    void fobiddenWithoutUserId() throws Exception {
      failure(reqGet("/"), HttpStatus.FORBIDDEN);
    }
  }

  @Nested
  @DisplayName("6. 쿠폰 사용")
  class UseCoupon {
    @Test
    @DisplayName("성공")
    void successDefault() throws Exception {
      success(reqPost("/CPIssued/use","user2"));
    }

    @Test
    @DisplayName("실패 - 쿠폰이 ISSUED 가 아님")
    void badRequest_coupon_not_issued() throws Exception {
      failure(reqPost("/CPUsed/use","user2"), HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("실패 - 쿠폰 유효기간 만료")
    void badRequest_coupon_expired() throws Exception {
      failure(reqPost("/CPUserExpired/use","user2"), HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("실패 - 쿠폰 발행자가 아님")
    void forbidden_not_issuer() throws Exception {
      failure(reqPost("/CPIssued/use","user1"), HttpStatus.FORBIDDEN);
    }
  }

  @Nested
  @DisplayName("5. 쿠폰 다운로드")
  class DownloadCoupon {
    @Test
    @DisplayName("성공")
    void successDefault() throws Exception {
      success(reqPost("/CP3000/download","user1"));
    }

    @Test
    @DisplayName("실패 - 쿠폰 그룹이 PUBLISHED 가 아님")
    void badRequest_couponGroup_not_published() throws Exception {
      failure(reqPost("/CP1000/download","user1"), HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("실패 - 발급 갯수 모두 소진")
    void badRequest_coupons_all_used() throws Exception {
      failure(reqPost("/CPCompleted/download","user1"), HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("실패 - 유효기간 만료")
    void badRequest_couponGroup_expired() throws Exception {
      failure(reqPost("/CPExpired/download","user1"), HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("실패 - 이미 다운로드 된 쿠폰")
    void badRequest_coupon_already_downloaded() throws Exception {
      failure(reqPost("/CP3000/download","user2"), HttpStatus.BAD_REQUEST);
    }
  }
}
