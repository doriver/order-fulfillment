package com.example.order_fulfillment.systems.oms.order.domain.entity;

import com.example.order_fulfillment.systems.dto.OrderReceiveDTO;
import com.example.order_fulfillment.systems.oms.product.domain.entity.SkuOms;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "idx_order", columnList = "order_id"),
        @Index(name = "idx_sku_oms", columnList = "sku_oms_code")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private Order order;

    @Column(nullable = false)
    private String productCode;

    @Column(nullable = false, length = 200)
    private String productName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_oms_code", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private SkuOms skuOms;

    @Column(nullable = false)
    private int quantity;

    @Column(length = 50)
    private String volume;  // 용량

    private long weight;    // 무게 (g)

    @Column(nullable = false)
    private long unitPrice;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",
            insertable = false, updatable = false // DB가 직접 입력하므로 ,JPA는 신경 끄라는 의미
    )
    private LocalDateTime updatedAt;

    public static OrderItem from(Order order, OrderReceiveDTO.OrderItemDTO dto, SkuOms skuOms) {
        OrderItem item = new OrderItem();
        item.order = order;
        item.productCode = dto.productCode();
        item.productName = dto.productName();
        item.skuOms = skuOms;
        item.quantity = dto.quantity();
        item.volume = dto.volume();
        item.weight = dto.weight();
        item.unitPrice = dto.unitPrice();
        return item;
    }
}
