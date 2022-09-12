package com.fevertime.coinvillage.dto.stock;

import com.fevertime.coinvillage.domain.account.Stock;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockResponseDto {
    private Long stockId;

    private String content;

    private String description;

    private Long price;

    private Long variable;

    private String createdAt;

    public StockResponseDto(Stock stock) {
        this.stockId = stock.getStockId();
        this.content = stock.getContent();
        this.description = stock.getDescription();
        this.price = stock.getPrice();
        this.variable = stock.getVariable();
        this.createdAt = stock.getCreatedAt().format(DateTimeFormatter.ofPattern("yy.MM.dd"));
    }
}
