package com.fevertime.coinvillage.dto.stock.nation;

import com.fevertime.coinvillage.domain.account.Stock;
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

    public StockNationResponseDto(Stock stock) {
        this.stockId = stock.getStockId();
        this.content = stock.getContent();
        this.price = stock.getPrice();
        if (stock.getStockHistoryList().size() - 1 == -1) {
            this.percent = 0.0;
        } else {
            this.percent = (stock.getPrice().doubleValue()
                    - stock.getStockHistoryList().get(stock.getStockHistoryList().size() - 1).getPrice().doubleValue()) / stock.getPrice().doubleValue() * 100;
        }
        if (stock.getStockHistoryList().size() - 1 == -1) {
            this.gap = 0L;
        } else {
            this.gap = stock.getPrice()
                    - stock.getStockHistoryList().get(stock.getStockHistoryList().size() - 1).getPrice();
        }
    }
}
