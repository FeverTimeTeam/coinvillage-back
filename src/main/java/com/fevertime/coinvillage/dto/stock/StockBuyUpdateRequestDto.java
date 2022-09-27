package com.fevertime.coinvillage.dto.stock;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockBuyUpdateRequestDto {
    private String content;

    private String description;

    private Long price;
}
