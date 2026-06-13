package com.example.order_fulfillment.systems.oms.zone.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "idx_zone", columnList = "parent_code")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Zone {
    @Id
    @Column(nullable = false, length = 20)
    private String code; // 권역 코드

    @Column(nullable = false, length = 100)
    private String name; // 권역명

    @Column(nullable = false, length = 100)
    private String region; // 해당 지역

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_code", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Zone parentZone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private RegionType regionType;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",
            insertable = false, updatable = false // DB가 직접 입력하므로 ,JPA는 신경 끄라는 의미
    )
    private LocalDateTime updatedAt;
}
