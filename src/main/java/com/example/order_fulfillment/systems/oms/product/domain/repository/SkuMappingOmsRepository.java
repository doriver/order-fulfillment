package com.example.order_fulfillment.systems.oms.product.domain.repository;

import com.example.order_fulfillment.systems.oms.channel.domain.entity.Channel;
import com.example.order_fulfillment.systems.oms.product.domain.entity.SkuMappingOms;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkuMappingOmsRepository extends JpaRepository<SkuMappingOms, Long> {
    Optional<SkuMappingOms> findByChannelAndChannelProductCode(Channel channel, String channelProductCode);
}
