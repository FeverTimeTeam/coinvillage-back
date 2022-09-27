package com.fevertime.coinvillage.dto.stock.nation;

import com.fevertime.coinvillage.domain.stock.CurrentStock;
import com.fevertime.coinvillage.domain.stock.Stock;
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
