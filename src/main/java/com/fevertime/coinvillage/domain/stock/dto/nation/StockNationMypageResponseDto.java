package com.fevertime.coinvillage.domain.stock.dto.nation;

import com.fevertime.coinvillage.domain.stock.entity.CurrentStock;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockNationMypageResponseDto {
    private Long currentStockId;

    private String content;

    private String description;

    private Long price;

    private Long count;

    private Long stockTotal;

    public StockNationMypageResponseDto(CurrentStock currentStock) {
        this.currentStockId = currentStock.getCurrentStockId();
        this.content = currentStock.getContent();
        this.description = currentStock.getDescription();
        this.price = currentStock.getPrice();
        this.count = currentStock.getCount();
    }
}
