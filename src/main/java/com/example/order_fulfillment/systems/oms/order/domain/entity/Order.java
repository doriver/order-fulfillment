package com.example.order_fulfillment.systems.oms.order.domain.entity;

import com.example.order_fulfillment.systems.oms.channel.domain.entity.Channel;
import com.example.order_fulfillment.systems.oms.order.domain.entity.embed.Buyer;
import com.example.order_fulfillment.systems.oms.order.domain.entity.embed.OrderDelivery;
import com.example.order_fulfillment.systems.integration.dto.OrderReceiveDTO;
import com.example.order_fulfillment.systems.oms.zone.domain.entity.Zone;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "orders",
        indexes = {
                @Index(name = "idx_channel", columnList = "channel_id")
                , @Index(name = "idx_delivery_zone", columnList = "delivery_zone_code")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    @NotNull
    private Channel channel;

    @Column(nullable = false)
    private Long storeOrderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private OrderStatus status;

    @Column(nullable = false)
    private LocalDateTime paidAt;

    @Embedded
    private Buyer buyer;

    @Embedded
    private OrderDelivery orderDelivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_zone_code", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), nullable = false)
    @NotNull
    private Zone zone;

    @Column(nullable = false)
    private long totalAmount;

    @Column(nullable = false)
    private long actualPayment;

    @Column(nullable = false)
    private int shippingFee;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",
            insertable = false, updatable = false // DB가 직접 입력하므로 ,JPA는 신경 끄라는 의미
    )
    private LocalDateTime updatedAt;

    public static Order from(Channel channel, OrderReceiveDTO dto, Zone zone) {
        Order order = new Order();
        order.channel = channel;
        order.storeOrderId = dto.storeOrderId();
        order.status = OrderStatus.PAID;
        order.paidAt = dto.paidAt();
        order.buyer = new Buyer(dto.buyerName(), dto.buyerPhone(), dto.buyerEmail());
        order.orderDelivery = new OrderDelivery(
                dto.receiverName(), dto.receiverPhone(),
                dto.receiverAddress(), dto.receiverAddressDetail(),
                dto.deliveryMemo()
        );
        order.zone = zone;
        order.totalAmount = dto.totalAmount();
        order.actualPayment = dto.actualPayment();
        order.shippingFee = dto.shippingFee();

        return order;
    }
}
