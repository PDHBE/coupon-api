# 개요
- 쿠폰을 관리하고 다운로드 및 사용할 수 있는 API 서버입니다.
- 쿠폰은 선착순으로 받을 수 있으며, 사용 시 사용자의 포인트에 적립됩니다.

# 기능 목록
- 쿠폰 그룹 생성
- 쿠폰 그룹 수정
- 쿠폰 그룹 발행
- 쿠폰 그룹 비활성화
- 쿠폰 다운로드
- 쿠폰 사용
- 보유 쿠폰 목록 조회

# 기능 상세 설명

## 공통
- 모든 API는 해더 "X-USER-ID" 값이 필수입니다. 값이 없을 경우 "권한 없음(403)"으로 응답합니다.
- PathVariable의 쿠폰 코드는 쿠폰 그룹과 쿠폰의 code값으로 사용됩니다.

<img width="574" alt="스크린샷 2022-01-02 오후 10 31 04" src="https://user-images.githubusercontent.com/82703938/147877394-2356be16-648f-4f7b-956f-bb59a0e33bff.png">

- 정상처리 및 오류처리 모두 success 필드를 포함합니다.
    - 정상처리라면 true, 오류처리라면 false 값을 출력합니다.
- 정상처리는 response 필드를 포함하고 error 필드는 null입니다.
- 오류처리는 error 필드를 포함하고 response 필드는 null입니다.
- error 필드는 status, message 필드를 포함합니다.
    - status : HTTP Response status code 값과 동일한 값을 출력해야 합니다.
    - message : 오류 메시지가 출력됩니다.

// 정상 처리
```json
{
  "success": true,
  "response": {
    "id": 1,
    "issuer_id": "issuer1",
    "code": "CP1000",
    "name": "1000 포인트 쿠폰",
    "status": "CREATED",
    "amount": 1000,
    "max_count": 100,
    "current_count": 0,
    "valid_from_dt": "2021-01-01T00:00:00.000Z",
    "valid_to_dt": "2021-12-31T23:59:59.000Z",
    "created_at": "2021-10-01T09:00:00.000Z",
    "updated_at": null,
  },
  "error": null,
}
```

// 오류 처리
```json
{
  "success": false,
  "response": null,
  "error": {
    "status": 400,
    "message": "잘못된 요청입니다."
  }
}
```

## 1. 쿠폰 그룹 생성
- 쿠폰명, 지급 포인트, 쿠폰 발행 개수, 유효기간 시작일/종료일을 받아서 쿠폰 그룹을 생성합니다.
- Path Variable code는 쿠폰 코드이며, 영숫자 형식으로 최소 2글자에서 최대 50글자 이내여야 합니다.
- 쿠폰 코드는 중복될 수 없습니다. 중복인 경우 400 에러를 리턴합니다.
- X-USER-ID 값을 issuer_id로 사용하고 쿠폰의 issuer_id로 저장합니다.
- 상태 값을 CREATED로 저장합니다.
- 응답으로 생성된 쿠폰 그룹 정보를 내려줍니다.

<img width="539" alt="스크린샷 2022-01-02 오후 10 34 18" src="https://user-images.githubusercontent.com/82703938/147877466-68a08405-5d35-48d2-926e-f80117f59ae9.png">

```json
{
  "name": "1000 포인트 쿠폰",
  "amount": 1000,
  "max_count": 100,
  "valid_from_dt": "2021-01-01T00:00:00.000Z",
  "valid_to_dt": "2021-12-31T23:59:59.000Z",
}
```

Response Body:

```json
{
  "success": true,
  "response": {
    "id": 1,
    "issuer_id": "issuer1",
    "code": "CP1000",
    "name": "1000 포인트 쿠폰",
    "status": "CREATED",
    "amount": 1000,
    "max_count": 100,
    "current_count": 0,
    "valid_from_dt": "2021-01-01T00:00:00.000Z",
    "valid_to_dt": "2021-12-31T23:59:59.000Z",
    "created_at": "2021-10-01T09:00:00.000Z",
    "updated_at": null,
  },
  "error": null,
}
```

## 2. 쿠폰 그룹 수정
- 쿠폰명, 지급 포인트, 쿠폰 발행 개수, 유효기간 시작일/종료일을 받아서 쿠폰 그룹을 수정합니다.
- X-USER-ID 값을 issuer_id로 사용하며 쿠폰의 issuer_id와 같아야 합니다.
- CREATED 상태인 경우만 수정할 수 있습니다.
- 응답으로 수정된 쿠폰 그룹 정보를 내려줍니다.

<img width="529" alt="스크린샷 2022-01-02 오후 10 35 16" src="https://user-images.githubusercontent.com/82703938/147877491-7f438795-8e44-4f08-b01b-b11418fee063.png">

```json
{
  "name": "1000 포인트 쿠폰",
  "amount": 1000,
  "max_count": 100,
  "valid_from_dt": "2021-01-01T00:00:00.000Z",
  "valid_to_dt": "2021-12-31T23:59:59.000Z",
}
```


Response Body:

```json
{
  "success": true,
  "response": {
    "id": 1,
    "issuer_id": "issuer1",
    "code": "CP1000",
    "name": "1000 포인트 쿠폰",
    "status": "CREATED",
    "amount": 1000,
    "max_count": 100,
    "current_count": 0,
    "valid_from_dt": "2021-01-01T00:00:00.000Z",
    "valid_to_dt": "2021-12-31T23:59:59.000Z",
    "created_at": "2021-10-01T09:00:00.000Z",
    "updated_at": "2021-10-01T09:00:00.000Z",
  },
  "error": null,
}
```

## 3. 쿠폰 그룹 발행
- CREATED 상태이고 유효기간이 만료되지 않은 쿠폰 그룹만 발행할 수 있습니다.
    - 발행이란 사용자가 쿠폰을 다운로드 할 수 있는 상태를 말합니다.
- X-USER-ID 값을 issuer_id로 사용하며 쿠폰 그룹의 issuer_id와 같아야 합니다.
- 상태 값을 PUBLISHED로 저장합니다.
- 응답으로 발행된 쿠폰 그룹 정보를 내려줍니다.

<img width="395" alt="스크린샷 2022-01-02 오후 10 36 17" src="https://user-images.githubusercontent.com/82703938/147877523-6dae346f-c8bd-4450-91be-1742fb5a2037.png">

```json
{
  "success": true,
  "response": {
    "id": 1,
    "issuer_id": "issuer1",
    "code": "CP1000",
    "name": "1000 포인트 쿠폰",
    "status": "PUBLISHED",
    "amount": 1000,
    "max_count": 100,
    "current_count": 0,
    "valid_from_dt": "2021-01-01T00:00:00.000Z",
    "valid_to_dt": "2021-12-31T23:59:59.000Z",
    "created_at": "2021-10-01T09:00:00.000Z",
    "updated_at": "2021-10-01T09:00:00.000Z",
  },
  "error": null,
}
```

## 4. 쿠폰 그룹 비활성화
- DISABLED 상태가 아닌 쿠폰 그룹만 비활성화 할 수 있습니다.
- X-USER-ID 값을 issuer_id로 사용하며 쿠폰의 issuer_id와 같아야 합니다.
- 상태 값을 DISABLED로 저장합니다.
- 응답으로 비활성화된 쿠폰 그룹 정보를 내려줍니다.

<img width="403" alt="스크린샷 2022-01-02 오후 10 37 01" src="https://user-images.githubusercontent.com/82703938/147877543-31a9c13f-5ebb-4519-a784-7b5b9104c8d4.png">

```json
{
  "success": true,
  "response": {
    "id": 1,
    "issuer_id": "issuer1",
    "code": "CP1000",
    "name": "1000 포인트 쿠폰",
    "status": "DISABLED",
    "amount": 1000,
    "max_count": 100,
    "current_count": 0,
    "valid_from_dt": "2021-01-01T00:00:00.000Z",
    "valid_to_dt": "2021-12-31T23:59:59.000Z",
    "created_at": "2021-10-01T09:00:00.000Z",
    "updated_at": "2021-10-01T09:00:00.000Z",
  },
  "error": null,
}
```

## 5. 쿠폰 다운로드
- 쿠폰 그룹이 PUBLISHED 상태이고 발급 개수가 남아있고 유효기간이 만료되지 않은 경우에 쿠폰을 받을 수 있습니다.
- X-USER-ID 값을 user_id로 사용하며 쿠폰의 user_id로 저장됩니다.
- 쿠폰은 중복으로 받을 수 없습니다. 이미 다운로드한 경우 400 에러를 리턴합니다.
- 유효기간은 쿠폰 그룹의 유효기간을 그대로 따라갑니다.
- 상태 값을 ISSUED로 저장합니다.
- 응답으로 발급된 쿠폰 정보를 내려줍니다.

<img width="398" alt="스크린샷 2022-01-02 오후 10 37 43" src="https://user-images.githubusercontent.com/82703938/147877569-acc0c349-e518-4695-a0b3-50cdf7f8838b.png">

```json
{
  "success": true,
  "response": {
    "id": 1,
    "user_id": "user1",
    "code": "CP1000",
    "name": "1000 포인트 쿠폰",
    "status": "ISSUED",
    "amount": 1000,
    "valid_from_dt": "2021-01-01T00:00:00.000Z",
    "valid_to_dt": "2021-12-31T23:59:59.000Z",
    "used_at": null,
    "created_at": "2021-10-01T09:00:00.000Z",
    "updated_at": "2021-10-01T09:00:00.000Z",
  },
  "error": null,
}
```

## 6. 쿠폰 사용
- ISSUED 상태와 유효기간 내의 쿠폰만 사용할 수 있습니다.
- X-USER-ID 값을 user_id로 사용하며 쿠폰의 user_id와 같아야 합니다.
- user_id는 users 테이블의 id와 일치합니다.
- 상태 값은 USED로 그리고 사용 시간은 현재 시각으로 저장합니다.
- 쿠폰의 amount 값을 사용자의 point에 추가합니다.
- 응답으로 사용된 쿠폰 정보를 내려줍니다.

<img width="357" alt="스크린샷 2022-01-02 오후 10 38 31" src="https://user-images.githubusercontent.com/82703938/147877592-c4837b40-c6e9-426c-8173-6635894eee7b.png">

```json
{
  "success": true,
  "response": {
    "id": 1,
    "user_id": "user1",
    "code": "CP1000",
    "name": "1000 포인트 쿠폰",
    "status": "USED",
    "amount": 1000,
    "valid_from_dt": "2021-01-01T00:00:00.000Z",
    "valid_to_dt": "2021-12-31T23:59:59.000Z",
    "used_at": "2021-10-01T09:00:00.000Z",
    "created_at": "2021-10-01T09:00:00.000Z",
    "updated_at": "2021-10-01T09:00:00.000Z",
  },
  "error": null,
}
```

## 7. 보유 쿠폰 목록 조회
- X-USER-ID 값을 user_id로 사용하며 쿠폰의 user_id와 같아야 합니다.
- page, size 값이 최솟값~최댓값 범위 밖이거나 주어지지 않는다면 기본값으로 대체합니다.
- 응답으로 조회된 쿠폰 목록을 내려줍니다.

<img width="543" alt="스크린샷 2022-01-02 오후 10 39 06" src="https://user-images.githubusercontent.com/82703938/147877605-0af0ba85-4c27-4ddc-a0a0-9b7944a3d319.png">

```json
{
  "success": true,
  "response": [{
    "id": 1,
    "user_id": "user1",
    "code": "CP1000",
    "name": "1000 포인트 쿠폰",
    "status": "ISSUED",
    "amount": 1000,
    "valid_from_dt": "2021-01-01T00:00:00.000Z",
    "valid_to_dt": "2021-12-31T23:59:59.000Z",
    "used_at": null,
    "created_at": "2021-10-01T09:00:00.000Z",
    "updated_at": null,
  }],
  "error": null,
}
```