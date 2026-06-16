package com.example.order_fulfillment.systems.oms.product.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SkuOms {
    @Id
    @Column(nullable = false, length = 100)
    private String code; // 내부 SKU 코드

    @Column(nullable = false, length = 200)
    private String name; // 상품명

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",
            insertable = false, updatable = false // DB가 직접 입력하므로 ,JPA는 신경 끄라는 의미
    )
    private LocalDateTime updatedAt;

    public static SkuOms create(String code, String name) {
        SkuOms sku = new SkuOms();
        sku.code = code;
        sku.name = name;
        return sku;
    }
}
