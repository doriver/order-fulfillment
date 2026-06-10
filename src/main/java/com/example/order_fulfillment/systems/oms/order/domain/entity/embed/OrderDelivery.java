package com.example.order_fulfillment.systems.oms.order.domain.entity.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDelivery {
    @Column(nullable = false, length = 50)
    private String receiverName;

    @Column(nullable = false, length = 20)
    private String receiverPhone;

    @Column(nullable = false, length = 200)
    private String receiverAddress;

    @Column(length = 100)
    private String receiverAddressDetail;

    @Column(length = 200)
    private String deliveryMemo;
}
