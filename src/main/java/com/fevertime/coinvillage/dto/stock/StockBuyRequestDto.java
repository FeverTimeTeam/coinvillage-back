package com.fevertime.coinvillage.dto.stock;

import com.fevertime.coinvillage.domain.stock.Stock;
import com.fevertime.coinvillage.domain.stock.StockBuy;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockBuyRequestDto {
    private String content;

    private String description;

    private Long price;

    @ApiModelProperty(hidden = true)
    private Long stockTotal;

    @ApiModelProperty(hidden = true)
    private Stock stock;

    public StockBuy toEntity() {
        return StockBuy.builder()
                .content(content)
                .description(description)
                .price(price)
                .stock(stock)
                .build();
    }
}
