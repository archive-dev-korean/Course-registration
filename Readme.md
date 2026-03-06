### 프로젝트 개요
- 기간: 약 1개월 내외(최대2달)
- 목표 트래픽 : VUs 1만명 가정
- 목적 : 대규모 동시 요청 환경에서 시스템 정합성과 안정성 보장 하는 백엔드 설계 및 구현

### API 명세서
<details>
<summary>펼치기</summary>
<div markdown>
    
## 공통

- Base URL: `/api/v1`
- Content-Type : `application/json`
- 인증 :  `Authoriazation: Bearer {JWT}`

## 공통 응답 포맷

- 성공: `{”data”:…}`
- 에러: `{ "error": { "code": "...", "message": "...", "details": ... } }`

# 인증(Auth)

| API | Method | URL | Auth | Request | Response(200) | 주요 에러 |
| --- | --- | --- | --- | --- | --- | --- |
| 로그인(JWT 발급) | POST | `/auth/login` | ❌ | `{"email":"a@b.com","password":"***"}` | `{"data":{"accessToken":"...","tokenType":"Bearer","expiresIn":3600}}` | 401 INVALID_CREDENTIALS |
| 내 정보 조회 | GET | `/auth/me` | ✅ | - | `{"data":{"userId":1,"name":"홍길동","email":"a@b.com","roles":["USER"]}}` | 401 UNAUTHORIZED |

# 강의(Course)

| API | Method | URL | Auth | Query/Body | Response(200) | 주요 에러 |
| --- | --- | --- | --- | --- | --- | --- |
| 강의 목록 조회 | GET | `/courses` | ❌(또는 ✅) | Query: `page` `size` `q`(검색어) `category` `status`(OPEN/CLOSED) | `{"data":{"items":[{...}],"page":0,"size":20,"totalElements":123}}` | 400 INVALID_PARAM |
| 강의 상세 조회 | GET | `/courses/{courseId}` | ❌(또는 ✅) | - | `{"data":{...}}` | 404 COURSE_NOT_FOUND |
| 강의 좌석/정원 조회(가벼운 엔드포인트) | GET | `/courses/{courseId}/capacity` | ❌(또는 ✅) | - | `{"data":{"courseId":10,"capacity":30,"enrolled":28,"available":2}}` | 404 COURSE_NOT_FOUND |

Course 객체 예시

```json
{
  "courseId": 10,
  "title": "Spring Boot 심화",
  "instructor": "Kim",
  "category": "BACKEND",
  "startAt": "2026-03-02T10:00:00+09:00",
  "endAt": "2026-03-02T12:00:00+09:00",
  "capacity": 30,
  "enrolled": 28,
  "status": "OPEN"
}
```

# 수강신청(Enrollment)

| API | Method | URL | Auth | Request | Response(200/201) | 주요 에러(핵심) |
| --- | --- | --- | --- | --- | --- | --- |
| 수강 신청 | POST | `/courses/{courseId}/enrollments` | ✅ | (Body optional) `{"note":"..."}` | **201** `{"data":{"enrollmentId":100,"courseId":10,"userId":1,"status":"ENROLLED","createdAt":"..."}}` | 409 ALREADY_ENROLLED, 409 CAPACITY_FULL, 409 ENROLLMENT_CLOSED, 404 COURSE_NOT_FOUND |
| 수강 취소 | DELETE | `/courses/{courseId}/enrollments/me` | ✅ | - | `{"data":{"courseId":10,"userId":1,"status":"CANCELED","canceledAt":"..."}}` | 404 ENROLLMENT_NOT_FOUND, 409 CANCEL_NOT_ALLOWED |
| 내 신청/취소 이력 조회 | GET | `/me/enrollments` | ✅ | Query: `page` `size` `status`(ENROLLED/CANCELED) | `{"data":{"items":[{...}],"page":0,"size":20,"totalElements":5}}` | 400 INVALID_PARAM |
| 강의별 신청자 목록(관리/운영용, 선택) | GET | `/courses/{courseId}/enrollments` | ✅(ADMIN) | Query: `page` `size` | `{"data":{"items":[{...}],"page":0,"size":20,"totalElements":28}}` | 403 FORBIDDEN, 404 COURSE_NOT_FOUND |

Enrollment 객체 예시

```json
{
  "enrollmentId": 100,
  "courseId": 10,
  "userId": 1,
  "status": "ENROLLED",
  "createdAt": "2026-02-20T17:20:00+09:00",
  "canceledAt": null
}
```

# 운영 규칙

| 규칙 | 설명 | 서버 처리 방식(권장) |
| --- | --- | --- |
| 중복 신청 방지 | 같은 유저가 같은 강의를 2번 신청 불가 | DB Unique(`(course_id, user_id)`) + 충돌 시 409 |
| 정원 초과 방지 | capacity 초과 신청 불가 | 락(낙관/비관) + 초과 시 409 CAPACITY_FULL |
| 신청 기간 | 오픈/마감 시간 외 신청 불가(2인 이상 권장) | 기간 체크 후 409 ENROLLMENT_CLOSED |
| 취소 후 재신청 | 정책에 따라 허용/불허 | 허용 시 `status=CANCELED` 레코드 처리 방식 정의(soft delete/상태값) |

# 에러 코드

| HTTP | code | message 예시 |
| --- | --- | --- |
| 400 | INVALID_PARAM | 잘못된 요청 파라미터입니다. |
| 401 | UNAUTHORIZED | 인증이 필요합니다. |
| 403 | FORBIDDEN | 권한이 없습니다. |
| 404 | COURSE_NOT_FOUND | 강의를 찾을 수 없습니다. |
| 404 | ENROLLMENT_NOT_FOUND | 신청 내역이 없습니다. |
| 409 | ALREADY_ENROLLED | 이미 신청한 강의입니다. |
| 409 | CAPACITY_FULL | 정원이 초과되었습니다. |
| 409 | ENROLLMENT_CLOSED | 신청 가능 시간이 아닙니다. |

</div>
</details>

### 아키텍처
<details>
<summary>펼치기</summary>
<div markdown="1">

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/24561bb5-7ccf-45ec-9749-194f0fef815d" />

</div>
</details>

### 기술 스택
- Backend
    - Spring Boot
        - 빌드 툴 : Gradle
    - Spring Web(REST API) → RestTemplate
    - Spring Data JPA
    - Spring Validation, Lombok
    - Spring Security + JWT 인증
  <!--  - QueryDSL(2인 이상일 때, 추가 고려중) -->
- DB/Cache
    - Postgres
    - Redis(캐시)
- Resilience/Observability
    - Resilience4j(Circuit Breaker, Timeout)
    - Spring Boot Actuator
   <!-- - 2인 기준 Grafana,Prometheus -->
<!-- 
    - DEV
    - Docker Compose(Postgres, Redis)
    - Jenkins(CI/CD) → 선택
    - 배포 전략 → Blue/Green, Rolling, Canary 중 선택
-->

### 도메인 범위(MVP 기능)

### 핵심 기능

1. 강의 목록/상세 조회
2. 수강 신청
3. 수강 취소
4. 정원(좌석) 관리
5. 중복 신청 방지
6. 신청/취소 이력 조회

### 품질 요구사항

- 동시성 제어(락)를 통한 정합성 보장
- 인증(JWT) 및 권한 관리
    - 관리자,신청자  나눠서
- 캐시를 통한 조회 성능 개선
- 서킷브레이커를 통한 장애 격리
- 통합 테스트 + API 문서
<!-- 1인 기준 : MVP 완성 및 핵심 품질 기능 증명 -->
    
**목표** 

- 완주 최우선(MVP 완성 + 동시성/장애 대응 1개씩 증명)
- 핵심 API 1~2개 부하 테스트(최소 단위 증명)
    
<!-- 
    **아키텍처**
    
    - 모듈러 모놀리식
    - 패키기 기반 계층 분리 → 도메인 경계 기반 으로 바꿀까 생각중
        - `Controller`
        - `Service`
        - `Repository`
        - `Domain`
-->
    
**데이터 및 동시성 제어**

- Postgres + Spring data JPA 사용
- 수강 신청 API에 락 적용
    - 낙관적 락 vs 비관적 락
- 선택한 락 방식 적용 근거 및 한계 ReadMe에 문서화
    
**캐시 전략**

- Redis 캐시 1개 API에 적용
- TTL 기반 캐시 적용
- 캐시 무효화는 최소 범위로 적용

**장애 대응**

- Circuit Breaker를 외부 연동 1개에 적용
- fallback 로직 사용
- Circuit Breaker 동작 로그로 확인

**테스트**

- 통합 테스트 2~3개 작성
    - 수강 신청 성공 테스트
    - 동시 신청 시 정원 초과 방지 테스트
    - 중복 신청 방지 테스트

**부하 테스트**

- 핵심 API 1개에 대해 부하 테스트 수행
- 결과 기록 및 ReadME에 포함

<!-- **산출물** -->
   <!-- - README
        - 락 선택 근거
        - 캐시 적용 이유
        - CircuitBreaker 적용 이유
        - 부하 테스트 결과 요약 -->
    
   <!-- **DevOps**
    
    - CI → github Actions
        ~~- 또는 JenkinsCI(선택)~~
    - Docker Compose(Postgres+Redis) → ~~CD 대체인데 CI/CD 해보고 싶음~~ 보류-->
  
