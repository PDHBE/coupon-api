package com.github.pdhbe.couponapi.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

public class CouponGroupControllerTest extends BaseTest {

    public CouponGroupControllerTest() {
        super("/coupon-groups");
    }

    @AfterAll
    static void getAllHistories(){
        System.out.println(
                findAllHistories()
        );
    }

    @Nested
    @DisplayName("4. 쿠폰 그룹 비활성화")
    class DisableCouponGroup {
        @Test
        @DisplayName("성공")
        void successDefault() throws Exception {
            success(reqPost("/CPDisable/disable", "issuer1", createValidCouponGroupJsonObject()));
        }

        @Test
        @DisplayName("실패 - 이미 DISABLED 상태")
        void badRequest_already_disabled() throws Exception {
            failure(reqPost("/CPDisabled/disable", "issuer1", createValidCouponGroupJsonObject()),HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("실패 - 발행자가 아님")
        void forbidden_not_issuer() throws Exception {
            failure(reqPost("/CPDisable/disable", "NotIssuer", createValidCouponGroupJsonObject()),HttpStatus.FORBIDDEN);
        }
    }

    @Nested
    @DisplayName("3. 쿠폰 그룹 발행")
    class PublishCouponGroup {
        @Test
        @DisplayName("성공")
        void successDefault() throws Exception {
            success(reqPost("/CPCreated/publish", "issuer1", createValidCouponGroupJsonObject()));
        }

        @Test
        @DisplayName("실패 - CREATE 상태가 아님")
        void badRequest_not_created() throws Exception {
            failure(reqPost("/CPPublished/publish", "issuer1", createValidCouponGroupJsonObject()),HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("실패 - 기간 만료")
        void badRequest_expired() throws Exception {
            failure(reqPost("/CPExpired/publish", "issuer1", createValidCouponGroupJsonObject()),HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("실패 - 발행자가 아님")
        void forbidden_not_issuer() throws Exception {
            failure(reqPost("/CPCreated/publish", "NotIssuer", createValidCouponGroupJsonObject()),HttpStatus.FORBIDDEN);
        }
    }

    @Nested
    @DisplayName("2. 쿠폰 그룹 수정")
    class ModifyCouponGroup {
        @Test
        @DisplayName("성공")
        void successDefault() throws Exception {
            success(reqPut("/CPCreated", "issuer1", createValidCouponGroupJsonObject()));
        }

        @Test
        @DisplayName("실패 - 발행자가 아님")
        void forbidden_not_issuer() throws Exception {
            failure(reqPut("/CPCreated", "NotIssuer", createValidCouponGroupJsonObject()),HttpStatus.FORBIDDEN);
        }

        @Test
        @DisplayName("실패 - CREATE 상태가 아님")
        void forbidden_not_created() throws Exception {
            failure(reqPut("/CPPublished", "issuer1", createValidCouponGroupJsonObject()),HttpStatus.BAD_REQUEST);
        }
    }

    @Nested
    @DisplayName("1. 쿠폰 그룹 생성")
    class CreateCouponGroup {
        @Test
        @DisplayName("성공")
        void successDefault() throws Exception {
            success(reqPost("/vaildCode", "issuer1", createValidCouponGroupJsonObject()));
        }

        @Test
        @DisplayName("실패 - 권한 없음 (w/o X-USER-ID)")
        void fobiddenWithoutUserId() throws Exception {
            failure(reqPost("/code"), HttpStatus.FORBIDDEN);
        }

        @Test
        @DisplayName("실패 - 유효하지 않은 코드 / 길이 제한(2~50글자) 미준수")
        void badRequest_invalid_code_length() throws Exception {
            String code = "123456789012345678901234567890123456789012345678901";
            failure(reqPost("/" + code, "issuer1", createValidCouponGroupJsonObject())
                    , HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("실패 - 유효하지 않은 코드 / 영숫자 미준수")
        void badRequest_invalid_code_value() throws Exception {
            String code = "code*%";
            failure(reqPost("/" + code, "issuer1", createValidCouponGroupJsonObject())
                    , HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("실패 - 이미 존재하는 코드")
        void badRequest_already_exists_code() throws Exception {
            String code = "CP1000";
            failure(reqPost("/" + code, "issuer1", createValidCouponGroupJsonObject())
                    , HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("실패 - 유효하지 않은 쿠폰 그룹 이름 ")
        void badRequest_invalid_couponGroup_name() throws Exception {
            JSONObject validCouponGroupJsonObject = createValidCouponGroupJsonObject();
            String name = "n";
            validCouponGroupJsonObject.put("name", name);
            failure(reqPost("/code", "issuer1", validCouponGroupJsonObject), HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("실패 - 유효하지 않은 쿠폰 그룹 지급 포인트 ")
        void badRequest_invalid_couponGroup_amount() throws Exception {
            JSONObject validCouponGroupJsonObject = createValidCouponGroupJsonObject();
            int amount = 0;
            validCouponGroupJsonObject.put("amount", amount);
            failure(reqPost("/code", "issuer1", validCouponGroupJsonObject)
                    , HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("실패 - 유효하지 않은 쿠폰 그룹 쿠폰 발행 갯수")
        void badRequest_invalid_couponGroup_max_count() throws Exception {
            JSONObject validCouponGroupJsonObject = createValidCouponGroupJsonObject();
            int max_count = 0;
            validCouponGroupJsonObject.put("max_count", max_count);
            failure(reqPost("/code", "issuer1", validCouponGroupJsonObject)
                    , HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("실패 - 유효하지 않은 쿠폰 그룹 유효기간 시작일")
        void badRequest_invalid_couponGroup_valid_from_dt() throws Exception {
            JSONObject validCouponGroupJsonObject = createValidCouponGroupJsonObject();
            String valid_from_dt = "2021-01-02T00:00:00.000Z";
            String valid_to_dt = "2021-01-01T00:00:00.000Z";
            validCouponGroupJsonObject.put("valid_from_dt", valid_from_dt);
            validCouponGroupJsonObject.put("valid_to_dt", valid_to_dt);
            failure(reqPost("/code", "issuer1", validCouponGroupJsonObject)
                    , HttpStatus.BAD_REQUEST);
        }

        @Test
        @DisplayName("실패 - 유효하지 않은 쿠폰 그룹 유효기간 종료일")
        void badRequest_invalid_couponGroup_valid_to_dt() throws Exception {
            JSONObject validCouponGroupJsonObject = createValidCouponGroupJsonObject();
            String valid_to_dt = OffsetDateTime.now().minusDays(1).toString();
            validCouponGroupJsonObject.put("valid_to_dt", valid_to_dt);
            failure(reqPost("/code", "issuer1", validCouponGroupJsonObject)
                    , HttpStatus.BAD_REQUEST);
        }
    }

    private JSONObject createValidCouponGroupJsonObject() {
        Map<String, Object> couponGroupMap = new HashMap<>();
        couponGroupMap.put("name", "1000 포인트 쿠폰");
        couponGroupMap.put("amount", 1000);
        couponGroupMap.put("max_count", 100);
        couponGroupMap.put("valid_from_dt", "2021-01-01T00:00:00.000Z");
        couponGroupMap.put("valid_to_dt", "2021-12-31T23:59:59.000Z");
        return new JSONObject(couponGroupMap);
    }
}
