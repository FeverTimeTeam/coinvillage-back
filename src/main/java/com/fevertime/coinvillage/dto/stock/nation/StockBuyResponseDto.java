package com.fevertime.coinvillage.dto.stock.nation;

import com.fevertime.coinvillage.domain.account.StockBuy;
import com.fevertime.coinvillage.domain.model.StateName;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockBuyResponseDto {
    private Long stockId;

    private String content;

    private StateName stateName;

    private Long count;

    private Long countCount;

    private Long total;

    private String createdAt;

    public StockBuyResponseDto(StockBuy stockBuy) {
        this.stockId = stockBuy.getStock().getStockId();
        this.content = stockBuy.getStock().getContent();
        this.stateName = stockBuy.getStateName();
        this.count = stockBuy.getCount();
        this.countCount = stockBuy.getTotal() / stockBuy.getStock().getPrice();
        this.total = stockBuy.getTotal();
        this.createdAt = stockBuy.getCreatedAt().format(DateTimeFormatter.ofPattern("MM.dd"));
    }
}
