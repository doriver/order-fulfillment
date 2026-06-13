package com.example.order_fulfillment.systems.oms.order.application;

import com.example.order_fulfillment.systems.oms.channel.domain.entity.Channel;
import com.example.order_fulfillment.systems.oms.channel.domain.repository.ChannelRepository;
import com.example.order_fulfillment.systems.oms.order.domain.entity.Order;
import com.example.order_fulfillment.systems.oms.order.domain.entity.OrderItem;
import com.example.order_fulfillment.systems.oms.order.domain.repository.OrderItemRepository;
import com.example.order_fulfillment.systems.oms.order.domain.repository.OrderRepository;
import com.example.order_fulfillment.systems.integration.dto.TmsOrderDTO;
import com.example.order_fulfillment.systems.integration.dto.WmsOrderDTO;
import com.example.order_fulfillment.systems.integration.dto.OrderReceiveDTO;
import com.example.order_fulfillment.systems.oms.product.domain.entity.SkuMappingOms;
import com.example.order_fulfillment.systems.oms.product.domain.repository.SkuMappingOmsRepository;
import com.example.order_fulfillment.systems.oms.zone.domain.entity.Zone;
import com.example.order_fulfillment.systems.oms.zone.domain.repository.ZoneRepository;
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
    private final ZoneRepository zoneRepository;
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
        Zone zone = zoneRepository.findByAddress(dto.receiverAddress())
                .orElseThrow(() -> new Expected5xxException(ErrorCode.NOT_FOUND_ZONE));

        Order order = orderRepository.save(Order.from(channel, dto, zone));

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

    /*
        주문 조회후, 특정 데이터들 WMS,TMS에게 전달
     */
    @Transactional(readOnly = true)
    public void sendToWmsTms(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new Expected4xxException(ErrorCode.NOT_FOUND_ORDER));

        List<OrderItem> items = orderItemRepository.findByOrder(order);

        WmsOrderDTO wmsOrderDTO = new WmsOrderDTO(
                items.stream().map(WmsOrderDTO.Item::from).toList()
        );

        TmsOrderDTO tmsOrderDTO = TmsOrderDTO.from(order);

        /*
            실제론 WMS, TMS에게 전송하는 로직 필요
            이벤트 기반 아키텍쳐 사용 예정

            메시지 브로커(Kafka / RabbitMQ) 사용
            이벤트를 발행하고, WMS/TMS가 각자 구독해서 처리
         */
    }
}
