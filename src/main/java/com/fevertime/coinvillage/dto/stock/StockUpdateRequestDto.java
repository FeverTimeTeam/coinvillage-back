package com.fevertime.coinvillage.dto.stock;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockUpdateRequestDto {
    private String content;

    private String description;

    private Long price;

    private Long variable;
}
