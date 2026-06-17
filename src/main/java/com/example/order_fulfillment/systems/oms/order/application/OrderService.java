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
import com.example.order_fulfillment.systems.integration.dto.CenterPossibleDTO;
import com.example.order_fulfillment.systems.integration.dto.OrderRoutePossibleDTO;
import com.example.order_fulfillment.systems.integration.dto.WmsOutboundDecisionDTO;
import com.example.order_fulfillment.systems.integration.dto.TmsDeliveryDecisionDTO;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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


    /*
        출고, 배송 결정
        - 배송코스를 순회하며, 모든 아이템에 대해 (availableCenterCodes ∩ loadingCenterCodes) 가 존재하면 해당 코스 확정
        - 아이템마다 출고 센터가 다를 수 있음
        - Schedule은 가장 작은 회차로 선택
     */
    public void determineOutboundDelivery(
            List<CenterPossibleDTO> centerPossibles,
            List<OrderRoutePossibleDTO> routePossibles
    ) {
        WmsOutboundDecisionDTO wmsOutboundDecisionDTO = null;
        TmsDeliveryDecisionDTO tmsDeliveryDecisionDTO = null;

        // 배송코스를 순회하며, 출고센터와 배송코스를 결정
        for (OrderRoutePossibleDTO route : routePossibles) {
            Set<String> loadingCenters = new HashSet<>(route.loadingCenterCodes());

            // 아이템별로 출고 센터 결정 (availableCenterCodes ∩ loadingCenterCodes 중 첫번째)
            List<WmsOutboundDecisionDTO.Item> decidedItems = new ArrayList<>();
            boolean allSatisfied = true;

            for (CenterPossibleDTO cp : centerPossibles) {
                String centerCode = cp.availableCenterCodes().stream()
                        .filter(loadingCenters::contains)
                        .findFirst()
                        .orElse(null);

                if (centerCode == null) {
                    allSatisfied = false;
                    break;
                }
                decidedItems.add(new WmsOutboundDecisionDTO.Item(cp.skuCode(), cp.quantity(), centerCode));
            }
            // 위 과정 통해, 현재 배송코스로 결정됨
            if (allSatisfied) {
                // 배송 회차 결정 - 지금은 가장 작은것으로 하게 해둠, 추후 바꿀수도
                OrderRoutePossibleDTO.Schedule schedule = route.schedules().stream()
                        .min(Comparator.comparingInt(OrderRoutePossibleDTO.Schedule::roundNumber))
                        .orElseThrow(() -> new Expected5xxException(ErrorCode.NO_AVAILABLE_ROUTE));
                // tms에 보낼 상차센터 목록 - 중복 있으면 제거해서
                List<String> usedCenterCodes = decidedItems.stream()
                        .map(WmsOutboundDecisionDTO.Item::centerCode)
                        .distinct()
                        .toList();
                // 결과
                // 실제로 wms, tms가 이행하는데 필요한 데이터들 추가로 담아야함(주문 관련 데이터 등등)
                wmsOutboundDecisionDTO = new WmsOutboundDecisionDTO(decidedItems);
                tmsDeliveryDecisionDTO = new TmsDeliveryDecisionDTO(route.code(), schedule.roundNumber(), usedCenterCodes);
            }
        }

        if (wmsOutboundDecisionDTO == null || tmsDeliveryDecisionDTO == null) {
            throw new Expected5xxException(ErrorCode.FAIL_DETERMINE_FULFILLMENT);
        }

        /*
            실제론 WMS, TMS에게 전송하는 로직 필요
         */


    }
}
