package com.fevertime.coinvillage.domain.stock.dto.nation;

import com.fevertime.coinvillage.domain.stock.entity.StockBuy;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockNationResponseDto {
    private Long stockId;

    private String content;

    private Long price;

    private Double percent;

    private Long gap;

    public StockNationResponseDto(StockBuy stockBuy) {
        this.stockId = stockBuy.getStockBuyId();
        this.content = stockBuy.getContent();
        this.price = stockBuy.getPrice();
        this.percent = (stockBuy.getStockHistoryList().size() - 1 == -1)
                ? 0.0
                : (stockBuy.getPrice().doubleValue()
                - stockBuy.getStockHistoryList().get(stockBuy.getStockHistoryList().size() - 1).getPrice().doubleValue())
                * 100 / stockBuy.getPrice().doubleValue();
        this.gap = (stockBuy.getStockHistoryList().size() - 1 == -1)
                ? 0L
                : stockBuy.getPrice()
                - stockBuy.getStockHistoryList().get(stockBuy.getStockHistoryList().size() - 1).getPrice();
    }
}
