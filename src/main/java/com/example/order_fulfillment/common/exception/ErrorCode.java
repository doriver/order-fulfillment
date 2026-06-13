package com.example.order_fulfillment.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/*
    자주사용되는 ExpectedException들 정리및 관리
 */

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    NOT_FOUND_DELIVERY(HttpStatus.INTERNAL_SERVER_ERROR, "배송정보를 찾을수 없습니다."),
    NOT_FOUND_CHANNEL(HttpStatus.BAD_REQUEST, "존재하지 않는 채널 코드입니다."),
    NOT_FOUND_ZONE(HttpStatus.INTERNAL_SERVER_ERROR, "매핑되는 권역이 없습니다."),
    NOT_FOUND_SKU_MAPPING(HttpStatus.INTERNAL_SERVER_ERROR, "SKU 매핑 정보가 없습니다."),
    NOT_FOUND_ORDER(HttpStatus.BAD_REQUEST, "존재하지 않는 주문입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
