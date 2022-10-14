package com.fevertime.coinvillage.domain.savings.dto;

import com.fevertime.coinvillage.domain.savings.entity.SavingsSetting;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterestResponseDto {
    private Long interest;

    public InterestResponseDto(SavingsSetting savingsSetting) {
        this.interest = savingsSetting.getInterest();
    }
}
