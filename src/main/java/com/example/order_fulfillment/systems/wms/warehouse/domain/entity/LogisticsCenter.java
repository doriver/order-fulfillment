package com.example.order_fulfillment.systems.wms.warehouse.domain.entity;

import com.example.order_fulfillment.systems.oms.zone.domain.entity.Zone;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(indexes = {
        @Index(name = "idx_zone", columnList = "zone_code")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LogisticsCenter {

    @Id
    @Column(nullable = false, length = 20)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_code", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    @NotNull
    private Zone zone;

    @Column(nullable = false, length = 100)
    private String name;

    public static LogisticsCenter create(String code, String name, Zone zone) {
        LogisticsCenter center = new LogisticsCenter();
        center.code = code;
        center.name = name;
        center.zone = zone;
        return center;
    }
}
