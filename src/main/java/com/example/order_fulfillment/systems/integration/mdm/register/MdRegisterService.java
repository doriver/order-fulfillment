package com.example.order_fulfillment.systems.integration.mdm.register;

import com.example.order_fulfillment.common.exception.ErrorCode;
import com.example.order_fulfillment.common.exception.Expected4xxException;
import com.example.order_fulfillment.systems.integration.mdm.register.dto.ChannelRegisterDTO;
import com.example.order_fulfillment.systems.integration.mdm.register.dto.DeliveryRouteRegisterDTO;
import com.example.order_fulfillment.systems.integration.mdm.register.dto.LogisticsCenterRegisterDTO;
import com.example.order_fulfillment.systems.integration.mdm.register.dto.ZoneRegisterDTO;
import com.example.order_fulfillment.systems.oms.channel.domain.entity.Channel;
import com.example.order_fulfillment.systems.oms.channel.domain.repository.ChannelRepository;
import com.example.order_fulfillment.systems.oms.zone.domain.entity.Zone;
import com.example.order_fulfillment.systems.oms.zone.domain.repository.ZoneRepository;
import com.example.order_fulfillment.systems.tms.route.domain.entity.DeliveryRoute;
import com.example.order_fulfillment.systems.tms.route.domain.entity.DeliveryRouteNode;
import com.example.order_fulfillment.systems.tms.route.domain.repository.DeliveryRouteNodeRepository;
import com.example.order_fulfillment.systems.tms.route.domain.repository.DeliveryRouteRepository;
import com.example.order_fulfillment.systems.wms.warehouse.domain.entity.LogisticsCenter;
import com.example.order_fulfillment.systems.wms.warehouse.domain.repository.LogisticsCenterRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
    master data 등록
 */
@Service
@RequiredArgsConstructor
public class MdRegisterService {

    private final ChannelRepository channelRepository;
    private final ZoneRepository zoneRepository;
    private final LogisticsCenterRepository logisticsCenterRepository;
    private final DeliveryRouteRepository deliveryRouteRepository;
    private final DeliveryRouteNodeRepository deliveryRouteNodeRepository;

    @Transactional
    public Long saveChannel(ChannelRegisterDTO dto) {
        Channel channel = Channel.create(dto.code(), dto.name());
        return channelRepository.save(channel).getId();
    }

    @Transactional
    public String saveZone(ZoneRegisterDTO dto) {
        Zone parentZone = null;
        if (dto.parentCode() != null) {
            parentZone = zoneRepository.findById(dto.parentCode())
                    .orElseThrow(() -> new Expected4xxException(ErrorCode.NOT_FOUND_ZONE));
        }
        Zone zone = Zone.create(dto.code(), dto.region(), dto.regionType(), parentZone);
        return zoneRepository.save(zone).getCode();
    }

    @Transactional
    public String saveLogisticsCenter(LogisticsCenterRegisterDTO dto) {
        Zone zone = zoneRepository.findById(dto.zoneCode())
                .orElseThrow(() -> new Expected4xxException(ErrorCode.NOT_FOUND_ZONE));
        LogisticsCenter center = LogisticsCenter.create(dto.code(), dto.name(), zone);
        return logisticsCenterRepository.save(center).getCode();
    }

    @Transactional
    public String saveDeliveryRoute(DeliveryRouteRegisterDTO dto) {
        DeliveryRoute route = deliveryRouteRepository.save(DeliveryRoute.create(dto.code(), dto.name()));

        List<DeliveryRouteNode> nodes = dto.nodes().stream()
                .map(nodeDto -> {
                    Zone zone = zoneRepository.findById(nodeDto.zoneCode())
                            .orElseThrow(() -> new Expected4xxException(ErrorCode.NOT_FOUND_ZONE));
                    return DeliveryRouteNode.create(route, zone, nodeDto.sequence());
                })
                .toList();
        deliveryRouteNodeRepository.saveAll(nodes);

        return route.getCode();
    }
}
