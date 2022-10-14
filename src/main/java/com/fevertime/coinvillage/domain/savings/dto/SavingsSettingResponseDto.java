package com.fevertime.coinvillage.domain.savings.dto;

import com.fevertime.coinvillage.domain.savings.entity.SavingsSetting;
import com.fevertime.coinvillage.domain.model.Term;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavingsSettingResponseDto {
    private Long settingsId;

    private Term term;

    private String day;

    private Long bill;

    private Long tax;

    private Long interest;

    private Long maturity;

    public SavingsSettingResponseDto(SavingsSetting savingsSetting) {
        this.settingsId = savingsSetting.getSettingsId();
        this.term = savingsSetting.getTerm();
        this.day = savingsSetting.getDay();
        this.interest = savingsSetting.getInterest();
        this.bill = savingsSetting.getBill();
        this.tax = savingsSetting.getSavings().getMember().getCountry().getTax();
        this.maturity = savingsSetting.getMaturity();
    }
}
