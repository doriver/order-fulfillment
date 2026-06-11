package com.example.order_fulfillment.systems.oms.product.domain.entity;

import com.example.order_fulfillment.systems.oms.channel.domain.entity.Channel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "idx_sku_oms", columnList = "sku_oms_code"),
        @Index(name = "idx_channel", columnList = "channel_id")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SkuMappingOms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_oms_code", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private SkuOms skuOms;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private Channel channel;

    @Column(nullable = false, length = 100)
    private String channelProductCode; // 판매처가 사용하는 상품 코드

    @Column(nullable = false, length = 200)
    private String channelProductName; // 판매처가 사용하는 상품명

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",
            insertable = false, updatable = false // DB가 직접 입력하므로 ,JPA는 신경 끄라는 의미
    )
    private LocalDateTime updatedAt;
}
