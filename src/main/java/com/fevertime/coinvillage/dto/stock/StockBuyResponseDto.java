package com.fevertime.coinvillage.dto.stock;

import com.fevertime.coinvillage.domain.stock.StockBuy;
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

    private String description;

    private Long price;

    private String createdAt;

    public StockBuyResponseDto(StockBuy stockBuy) {
        this.stockId = stockBuy.getStockBuyId();
        this.content = stockBuy.getContent();
        this.description = stockBuy.getDescription();
        this.price = stockBuy.getPrice();
        this.createdAt = stockBuy.getCreatedAt().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
    }
}
