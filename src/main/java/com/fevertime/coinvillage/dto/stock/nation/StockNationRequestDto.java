package com.fevertime.coinvillage.dto.stock.nation;

import com.fevertime.coinvillage.domain.model.StateName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockNationRequestDto {
    private Long count;

    private Long total;

    private StateName stateName;
}
