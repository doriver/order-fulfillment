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

    NOT_FOUND_DELIVERY(HttpStatus.INTERNAL_SERVER_ERROR, "배송정보를 찾을수 없습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
