DELETE FROM coupon_group_histories;
DELETE FROM coupon_groups;
DELETE FROM coupons;
DELETE FROM users;

-- User 데이터 생성
INSERT INTO users
(id, name, point, created_at)
VALUES
    ('issuer1', 'issuer1', 0, CURRENT_TIMESTAMP),
    ('issuer2', 'issuer2', 0, CURRENT_TIMESTAMP),
    ('user1', 'user1', 0, CURRENT_TIMESTAMP),
    ('user2', 'user2', 1000, CURRENT_TIMESTAMP),
    ('user3', 'user3', 0, CURRENT_TIMESTAMP);

-- CouponGroup 데이터 생성
INSERT INTO coupon_groups
(name, issuer_id, code, status, amount, max_count, current_count, valid_from_dt, valid_to_dt, created_at)
VALUES
    ('1000 포인트 쿠폰', 'issuer1', 'CP1000', 'CREATED', 1000, 100, 0, '2021-01-01 00:00:00', '2021-12-31 23:59:59', CURRENT_TIMESTAMP),
    ('3000 포인트 쿠폰', 'issuer1', 'CP3000', 'PUBLISHED', 3000, 100, 0,'2021-01-01 00:00:00', '2021-12-31 23:59:59', CURRENT_TIMESTAMP),
    ('발행 테스트 쿠폰', 'issuer1', 'CPCreated', 'CREATED', 1000, 100, 3, '2021-01-01 00:00:00', '2021-12-31 23:59:59', CURRENT_TIMESTAMP),
    ('비활성화 테스트 쿠폰', 'issuer1', 'CPDisable', 'CREATED', 1000, 100, 0, '2021-01-01 00:00:00', '2021-12-31 23:59:59', CURRENT_TIMESTAMP),
    ('발행된 쿠폰', 'issuer1', 'CPPublished', 'PUBLISHED', 1000, 100, 100, '2021-01-01 00:00:00', '2021-12-31 23:59:59', CURRENT_TIMESTAMP),
    ('선착순 발행 테스트 쿠폰', 'issuer1', 'CPRemainedOne', 'PUBLISHED', 1000, 100, 99, '2021-01-01 00:00:00', '2021-12-31 23:59:59', CURRENT_TIMESTAMP),
    ('만료된 쿠폰', 'issuer1', 'CPExpired', 'CREATED', 1000, 100, 0, '2021-01-01 00:00:00', '2021-01-01 00:00:00', CURRENT_TIMESTAMP),
    ('완료된 쿠폰', 'issuer1', 'CPCompleted', 'PUBLISHED', 1000, 100, 100, '2021-01-01 00:00:00', '2021-12-31 23:59:59', CURRENT_TIMESTAMP),
    ('비활성화된 쿠폰', 'issuer1', 'CPDisabled', 'DISABLED', 1000, 100, 0, '2021-01-01 00:00:00', '2021-12-31 23:59:59', CURRENT_TIMESTAMP);

-- Issued Coupon 데이터 생성
INSERT INTO coupons
(name, user_id, code, status, amount, used_at, valid_from_dt, valid_to_dt, created_at)
VALUES
    ('쿠폰 목록 조회1', 'user3', 'CP1', 'ISSUED', 1000, null, '2021-01-01 00:00:00', '2021-12-31 23:59:59', CURRENT_TIMESTAMP),
    ('쿠폰 목록 조회2', 'user3', 'CP2', 'ISSUED', 1000, null, '2021-01-01 00:00:00', '2021-12-31 23:59:59', CURRENT_TIMESTAMP),
    ('쿠폰 목록 조회3', 'user3', 'CP3', 'ISSUED', 1000, null, '2021-01-01 00:00:00', '2021-12-31 23:59:59', CURRENT_TIMESTAMP),
    ('쿠폰 목록 조회4', 'user3', 'CP4', 'ISSUED', 1000, null, '2021-01-01 00:00:00', '2021-12-31 23:59:59', CURRENT_TIMESTAMP),
    ('쿠폰 목록 조회5', 'user3', 'CP5', 'ISSUED', 1000, null, '2021-01-01 00:00:00', '2021-12-31 23:59:59', CURRENT_TIMESTAMP),
    ('쿠폰 목록 조회6', 'user3', 'CP6', 'ISSUED', 1000, null, '2021-01-01 00:00:00', '2021-12-31 23:59:59', CURRENT_TIMESTAMP),
    ('1000 포인트 쿠폰', 'user1', 'CP1000Used', 'USED', 1000, null, '2021-01-01 00:00:00', '2021-12-31 23:59:59', CURRENT_TIMESTAMP),
    ('3000 포인트 쿠폰', 'user2', 'CP3000', 'ISSUED', 3000, null, '2021-01-01 00:00:00', '2021-12-31 23:59:59', CURRENT_TIMESTAMP),
    ('발급된 쿠폰', 'user2', 'CPIssued', 'ISSUED', 1000, null, '2022-01-01 00:00:00', '2022-12-31 23:59:59', CURRENT_TIMESTAMP),
    ('만료된 쿠폰', 'user2', 'CPUserExpired', 'ISSUED', 1000, null, '2021-01-01 00:00:00', '2021-01-01 00:00:00', CURRENT_TIMESTAMP),
    ('사용된 쿠폰', 'user2', 'CPUsed', 'USED', 1000, CURRENT_TIMESTAMP, '2021-01-01 00:00:00', '2021-12-31 23:59:59', CURRENT_TIMESTAMP);
