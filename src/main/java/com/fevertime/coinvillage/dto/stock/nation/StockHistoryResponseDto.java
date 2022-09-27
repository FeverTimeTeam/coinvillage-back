package com.fevertime.coinvillage.dto.stock.nation;

import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.domain.stock.StockHistory;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockHistoryResponseDto {
    private Long stockHistoryId;

    private String content;

    private Long count;

    private Long price;

    private String stateName;

    private String total;

    public StockHistoryResponseDto(StockHistory stockHistory) {
        this.stockHistoryId = stockHistory.getStockHistoryId();
        this.content = stockHistory.getContent();
        this.count = stockHistory.getCount();
        this.price = stockHistory.getPrice();
        this.stateName = stockHistory.getStateName().toString();
        this.total = (stockHistory.getStateName() == StateName.DEPOSIT)
                ? "+" + stockHistory.getCount() * stockHistory.getPrice()
                : "-" + stockHistory.getCount() * stockHistory.getPrice();
    }
}
