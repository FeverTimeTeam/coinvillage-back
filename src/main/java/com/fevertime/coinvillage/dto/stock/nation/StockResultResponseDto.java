package com.fevertime.coinvillage.dto.stock.nation;

import com.fevertime.coinvillage.domain.account.Stock;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockResultResponseDto {
    private Long stockId;

    private String content;

    private String description;

    private Long price;

    private Long buyCount;

    private Long stockTotal;

    public StockResultResponseDto(Stock stock) {
        this.stockId = stock.getStockId();
        this.content = stock.getContent();
        this.description = stock.getDescription();
        this.price = stock.getPrice();
        this.buyCount = stock.getStockBuyList().get(stock.getStockBuyList().size() - 1).getCount();
        this.stockTotal = stock.getMember().getStockTotal();
    }
}
