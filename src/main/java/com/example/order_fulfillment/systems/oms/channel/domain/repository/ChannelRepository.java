package com.example.order_fulfillment.systems.oms.channel.domain.repository;

import com.example.order_fulfillment.systems.oms.channel.domain.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Optional<Channel> findByCode(String code);
}
