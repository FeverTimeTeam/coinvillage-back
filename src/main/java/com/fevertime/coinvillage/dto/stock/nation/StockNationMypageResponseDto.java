package com.fevertime.coinvillage.dto.stock.nation;

import com.fevertime.coinvillage.domain.account.Stock;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockNationMypageResponseDto {
    private Long stockId;

    private String content;

    private String description;

    private Long price;

    private Long buyCount;

    private Long stockTotal;

    public StockNationMypageResponseDto(Stock stock) {
        Long buyCount = 0L;
        for (int i = 0; i < stock.getStockBuyList().size(); i++) {
            buyCount += stock.getStockBuyList().get(i).getCount();
        }

        this.stockId = stock.getStockId();
        this.content = stock.getContent();
        this.description = stock.getDescription();
        this.price = stock.getPrice();
        this.buyCount = buyCount;
        this.stockTotal = stock.getMember().getStockTotal();
    }
}
