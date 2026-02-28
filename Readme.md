### 프로젝트 개요
- 기간: 약 1개월 내외(최대2달)
- 목표 트래픽 : VUs 1만명 가정
- 목적 : 대규모 동시 요청 환경에서 시스템 정합성과 안정성 보장 하는 백엔드 설계 및 구현

### 기술 스택

- Backend
    - Spring Boot
        - 빌드 툴 : Gradle
    - Spring Web(REST API) → RestTemplate
    - Spring Data JPA
    - Spring Validation, Lombok
    - Spring Security + JWT 인증
    - QueryDSL(2인 이상일 때, 추가 고려중)
- DB/Cache
    - Postgres
    - Redis(캐시)
- Resilience/Observability
    - Resilience4j(Circuit Breaker, Timeout)
    - Spring Boot Actuator
    - 2인 기준 Grafana,Prometheus
- DEV
    - Docker Compose(Postgres, Redis)
    - Jenkins(CI/CD) → 선택
    - 배포 전략 → Blue/Green, Rolling, Canary 중 선택

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
- 1인 기준 : MVP 완성 및 핵심 품질 기능 증명
    
    **목표** 
    
    - 완주 최우선(MVP 완성 + 동시성/장애 대응 1개씩 증명)
    - 핵심 API 1~2개 부하 테스트(최소 단위 증명)
    
    **아키텍처**
    
    - 모듈러 모놀리식
    - 패키기 기반 계층 분리 → 도메인 경계 기반 으로 바꿀까 생각중
        - `Controller`
        - `Service`
        - `Repository`
        - `Domain`
    
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
    
    **산출물**
    
    - API 명세서
    - README
        - 락 선택 근거
        - 캐시 적용 이유
        - CircuitBreaker 적용 이유
        - 부하 테스트 결과 요약
    
    **DevOps**
    
    - CI → github Actions
        - 또는 JenkinsCI(선택)
    - Docker Compose(Postgres+Redis) → CD 대체인데 CI/CD 해보고 싶음
