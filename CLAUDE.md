# Order Fulfillment


## 개발 컨벤션

### DB / JPA 규칙

- FK 제약조건을 사용하지 않는다. 연관관계 컬럼에는 `@ForeignKey(ConstraintMode.NO_CONSTRAINT)`를 명시한다.
- FK 대신 참조 컬럼에 `@Index`를 사용한다.
- JPA 연관관계는 `@ManyToOne(fetch = FetchType.LAZY)` 단방향만 사용한다. `@OneToMany`, `@OneToOne`, `@ManyToMany` 양방향 매핑은 사용하지 않는다.

### 예외 처리

- 예외는 `Expected4xxException` (클라이언트 오류) 또는 `Expected5xxException` (서버 오류)을 사용한다.
- 자주 사용되는 예외 메시지는 `ErrorCode` enum에 정의해서 사용한다.
- 특별한 경우는 위 경우를 따르지 않는다.

### 기타

- 주석을 니 마음대로 지우지 마.