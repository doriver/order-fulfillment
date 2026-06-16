package com.example.order_fulfillment.systems.wms.warehouse.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Location {

    @Id
    @Column(nullable = false, length = 100)
    private String code;


}
