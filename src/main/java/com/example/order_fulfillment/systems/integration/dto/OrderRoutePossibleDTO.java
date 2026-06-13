package com.example.order_fulfillment.systems.integration.dto;

import com.example.order_fulfillment.systems.tms.route.domain.entity.DeliveryRoute;
import com.example.order_fulfillment.systems.tms.route.domain.entity.LoadingCenter;
import com.example.order_fulfillment.systems.tms.route.domain.entity.RouteSchedule;

import java.time.LocalTime;
import java.util.List;
/*
    주문 배송위한
    배송 코스의 회차들, 상차가능 센터들
 */
public record OrderRoutePossibleDTO(
        String code,
        String name,
        List<Schedule> schedules,
        List<String> loadingCenterCodes
) {
    public record Schedule(
            int roundNumber,
            LocalTime departureTime
    ) {
        public static Schedule from(RouteSchedule routeSchedule) {
            return new Schedule(routeSchedule.getRoundNumber(), routeSchedule.getDepartureTime());
        }
    }

    public static OrderRoutePossibleDTO of(
            DeliveryRoute route,
            List<RouteSchedule> routeSchedules,
            List<LoadingCenter> loadingCenters
    ) {
        return new OrderRoutePossibleDTO(
                route.getCode(),
                route.getName(),
                routeSchedules.stream().map(Schedule::from).toList(),
                loadingCenters.stream().map(lc -> lc.getLogisticsCenter().getCode()).toList()
        );
    }
}
