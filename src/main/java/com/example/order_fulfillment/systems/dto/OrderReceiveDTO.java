package com.example.order_fulfillment.systems.dto;

import java.time.LocalDateTime;
import java.util.List;
/*
    판매처의 결제완료된 주문을 oms에서 받기위해 사용되는 데이터
 */
public record OrderReceiveDTO(
        String storeCode,          // 판매처 코드
        Long storeOrderId,         // 판매처 주문 ID
        LocalDateTime paidAt,      // 결제완료 일시

        String buyerName,          // 구매자 이름
        String buyerPhone,         // 구매자 연락처
        String buyerEmail,         // 구매자 이메일

        String receiverName,       // 수령인 이름
        String receiverPhone,      // 수령인 연락처
        String receiverAddress,    // 배송 주소
        String receiverAddressDetail, // 배송 상세 주소
        String deliveryMemo,       // 배송 메모

        List<OrderItemDTO> items,  // 주문 상품 목록

        long totalAmount,          // 총 주문 금액
        long actualPayment,        // 실 결제 금액
        int shippingFee            // 배송비
) {
    public record OrderItemDTO(
            String productCode,      // 판매처 상품 코드
            String productName,    // 상품명
            int quantity,          // 수량
            String volume,         // 용량
            long weight,           // 무게
            long unitPrice         // 단가
    ) {}
}
