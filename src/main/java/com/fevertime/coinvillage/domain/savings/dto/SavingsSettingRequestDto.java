package com.fevertime.coinvillage.domain.savings.dto;

import com.fevertime.coinvillage.domain.model.Term;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SavingsSettingRequestDto {
    private Term term;

    private Long bill;

    private Long tax;

    private String day;

    private Long interest;
}
