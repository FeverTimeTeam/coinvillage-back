package com.fevertime.coinvillage.dto.savings;

import com.fevertime.coinvillage.domain.savings.SavingsSetting;
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
