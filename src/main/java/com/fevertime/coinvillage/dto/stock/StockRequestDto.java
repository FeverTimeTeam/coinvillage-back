package com.fevertime.coinvillage.dto.stock;

import com.fevertime.coinvillage.domain.account.Stock;
import com.fevertime.coinvillage.domain.member.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockRequestDto {
    private String content;

    private String description;

    private Long price;

    private Member member;

    public Stock toEntity() {
        return Stock.builder()
                .content(content)
                .description(description)
                .price(price)
                .member(member)
                .build();
    }
}
