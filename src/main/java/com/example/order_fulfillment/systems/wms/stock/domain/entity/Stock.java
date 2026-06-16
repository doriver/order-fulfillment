package com.example.order_fulfillment.systems.wms.stock.domain.entity;

import com.example.order_fulfillment.systems.wms.warehouse.domain.entity.Location;
import com.example.order_fulfillment.systems.wms.warehouse.domain.entity.Warehouse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(indexes = {
        @Index(name = "idx_sku", columnList = "sku_code"),
        @Index(name = "idx_location", columnList = "location_code"),
        @Index(name = "idx_warehouse", columnList = "warehouse_code")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sku_code", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private Sku sku;

    private long count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_code", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_code", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    private Warehouse warehouse;
}
