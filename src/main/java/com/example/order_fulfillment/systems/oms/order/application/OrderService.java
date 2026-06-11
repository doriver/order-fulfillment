package com.example.order_fulfillment.systems.oms.order.application;

import com.example.order_fulfillment.systems.oms.channel.domain.entity.Channel;
import com.example.order_fulfillment.systems.oms.channel.domain.repository.ChannelRepository;
import com.example.order_fulfillment.systems.oms.order.domain.entity.Order;
import com.example.order_fulfillment.systems.oms.order.domain.entity.OrderItem;
import com.example.order_fulfillment.systems.oms.order.domain.repository.OrderItemRepository;
import com.example.order_fulfillment.systems.oms.order.domain.repository.OrderRepository;
import com.example.order_fulfillment.systems.oms.order.presentation.dto.OrderReceiveDTO;
import com.example.order_fulfillment.systems.oms.product.domain.entity.SkuMappingOms;
import com.example.order_fulfillment.systems.oms.product.domain.repository.SkuMappingOmsRepository;
import com.example.order_fulfillment.systems.oms.zone.domain.entity.DeliveryZone;
import com.example.order_fulfillment.systems.oms.zone.domain.repository.DeliveryZoneRepository;
import com.example.order_fulfillment.common.exception.ErrorCode;
import com.example.order_fulfillment.common.exception.Expected4xxException;
import com.example.order_fulfillment.common.exception.Expected5xxException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ChannelRepository channelRepository;
    private final DeliveryZoneRepository deliveryZoneRepository;
    private final SkuMappingOmsRepository skuMappingOmsRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    /*
        판매처에서 받은 주문을, 시스템에 맞게 저장
        - 배송권역, sku 매핑 필요
     */
    @Transactional
    public Long ingestionOrder(OrderReceiveDTO dto) {
        Channel channel = channelRepository.findByCode(dto.storeCode())
                .orElseThrow(() -> new Expected4xxException(ErrorCode.NOT_FOUND_CHANNEL));
        // 배송권역 매핑
        DeliveryZone deliveryZone = deliveryZoneRepository.findByAddress(dto.receiverAddress())
                .orElseThrow(() -> new Expected5xxException(ErrorCode.NOT_FOUND_DELIVERY_ZONE));

        Order order = orderRepository.save(Order.from(channel, dto, deliveryZone));

        List<OrderItem> items = dto.items().stream()
                .map(itemDto -> {
                    // sku 매핑
                    SkuMappingOms mapping = skuMappingOmsRepository
                            .findByChannelAndChannelProductCode(channel, itemDto.productCode())
                            .orElseThrow(() -> new Expected5xxException(ErrorCode.NOT_FOUND_SKU_MAPPING));
                    return OrderItem.from(order, itemDto, mapping.getSkuOms());
                })
                .toList();

        orderItemRepository.saveAll(items);

        return order.getId();
    }
}
