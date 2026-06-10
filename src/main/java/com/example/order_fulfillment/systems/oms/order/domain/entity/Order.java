package com.example.order_fulfillment.systems.oms.order.domain.entity;

import com.example.order_fulfillment.systems.oms.channel.domain.entity.Channel;
import com.example.order_fulfillment.systems.oms.order.domain.entity.embed.Buyer;
import com.example.order_fulfillment.systems.oms.order.domain.entity.embed.OrderDelivery;
import com.example.order_fulfillment.systems.store.OrderManageDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "orders",
        indexes = {
                @Index(name = "idx_channel", columnList = "channel_id")
        }
)
@EntityListeners(AuditingEntityListener.class)
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

    @Column(nullable = false)
    private long totalAmount;

    @Column(nullable = false)
    private long actualPayment;

    @Column(nullable = false)
    private int shippingFee;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public static Order from(Channel channel, OrderManageDTO dto) {
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
        order.totalAmount = dto.totalAmount();
        order.actualPayment = dto.actualPayment();
        order.shippingFee = dto.shippingFee();

        return order;
    }
}
