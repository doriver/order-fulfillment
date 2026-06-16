package com.example.order_fulfillment.systems.wms.stock.application;

import com.example.order_fulfillment.systems.integration.dto.WmsOrderDTO;
import com.example.order_fulfillment.systems.integration.dto.CenterPossibleDTO;
import com.example.order_fulfillment.systems.wms.stock.domain.entity.Stock;
import com.example.order_fulfillment.systems.wms.stock.domain.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    // 아이템별 해당 재고가 있는 센터들 매핑
    @Transactional(readOnly = true)
    public List<CenterPossibleDTO> centerJudgment(WmsOrderDTO dto) {
        List<String> skuCodes = dto.items().stream()
                .map(WmsOrderDTO.Item::skuOmsCode)
                .toList();

        // skuCode → 재고 목록
        Map<String, List<Stock>> stocksBySkuCode = stockRepository.findBySku_CodeIn(skuCodes)
                .stream()
                .collect(Collectors.groupingBy(stock -> stock.getSku().getCode()));

        return dto.items().stream()
                .map(item -> {
                    // 센터별 재고 합산 후 요청수량 이상인 센터만 추출
                    List<String> centerCodes = stocksBySkuCode
                            .getOrDefault(item.skuOmsCode(), List.of())
                            .stream()
                            .collect(Collectors.groupingBy(
                                    stock -> stock.getWarehouse().getCenter().getCode(),
                                    Collectors.summingLong(Stock::getCount)
                            ))
                            .entrySet().stream()
                            .filter(entry -> entry.getValue() >= item.quantity())
                            .map(Map.Entry::getKey)
                            .toList();
                    return new CenterPossibleDTO(item.productName(), item.quantity(), item.skuOmsCode(), centerCodes);
                })
                .toList();
    }
}
