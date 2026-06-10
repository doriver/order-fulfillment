# Order Fulfillment

## DB / JPA 규칙

- FK 제약조건을 사용하지 않는다. 연관관계 컬럼에는 `@ForeignKey(ConstraintMode.NO_CONSTRAINT)`를 명시한다.
- FK 대신 참조 컬럼에 `@Index`를 사용한다.
- JPA 연관관계는 `@ManyToOne(fetch = FetchType.LAZY)` 단방향만 사용한다. `@OneToMany`, `@OneToOne`, `@ManyToMany` 양방향 매핑은 사용하지 않는다.
