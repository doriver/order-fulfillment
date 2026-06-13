package com.example.order_fulfillment.systems.tms.route.application;

import com.example.order_fulfillment.systems.integration.dto.TmsOrderDTO;
import com.example.order_fulfillment.systems.integration.dto.OrderRoutePossibleDTO;
import com.example.order_fulfillment.systems.tms.route.domain.entity.DeliveryRoute;
import com.example.order_fulfillment.systems.tms.route.domain.entity.LoadingCenter;
import com.example.order_fulfillment.systems.tms.route.domain.entity.RouteSchedule;
import com.example.order_fulfillment.systems.tms.route.domain.repository.DeliveryRouteNodeRepository;
import com.example.order_fulfillment.systems.tms.route.domain.repository.LoadingCenterRepository;
import com.example.order_fulfillment.systems.tms.route.domain.repository.RouteScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final DeliveryRouteNodeRepository deliveryRouteNodeRepository;
    private final RouteScheduleRepository routeScheduleRepository;
    private final LoadingCenterRepository loadingCenterRepository;

    /*
        배송지에 맞는 배송코스들 판단후, 회차들과 상차가능 센터들을 판단
     */
    @Transactional(readOnly = true)
    public List<OrderRoutePossibleDTO> routeJudgment(TmsOrderDTO dto) {
        // 1. 배송지에 맞는 배송코스들 판단 - deliveryZoneCode로 DeliveryRoute 조회
        List<DeliveryRoute> activeRoutes = deliveryRouteNodeRepository
                .findByZone_Code(dto.deliveryZoneCode())
                .stream()
                .map(node -> node.getDeliveryRoute())
                .filter(DeliveryRoute::isActive)
                .distinct()
                .toList();

        // 2. active 코스들의 회차들과 상차가능 센터들을 판단 - 회차, 상차가능 센터 조회
        List<RouteSchedule> schedules = routeScheduleRepository.findByDeliveryRouteIn(activeRoutes);
        List<LoadingCenter> loadingCenters = loadingCenterRepository.findByDeliveryRouteIn(activeRoutes);

        Map<String, List<RouteSchedule>> schedulesByRoute = schedules.stream()
                .collect(Collectors.groupingBy(s -> s.getDeliveryRoute().getCode()));
        Map<String, List<LoadingCenter>> loadingCentersByRoute = loadingCenters.stream()
                .collect(Collectors.groupingBy(lc -> lc.getDeliveryRoute().getCode()));

        // 3. DTO 조립
        return activeRoutes.stream()
                .map(route -> OrderRoutePossibleDTO.of(
                        route,
                        schedulesByRoute.getOrDefault(route.getCode(), List.of()),
                        loadingCentersByRoute.getOrDefault(route.getCode(), List.of())
                ))
                .toList();
    }
}
