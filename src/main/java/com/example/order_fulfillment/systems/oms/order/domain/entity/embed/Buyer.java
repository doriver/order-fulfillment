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
public class Buyer {
    @Column(nullable = false, length = 50)
    private String buyerName;

    @Column(nullable = false, length = 20)
    private String buyerPhone;

    @Column(length = 100)
    private String buyerEmail;
}
