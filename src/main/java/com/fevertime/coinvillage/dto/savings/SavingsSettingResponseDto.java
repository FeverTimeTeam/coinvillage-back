package com.fevertime.coinvillage.dto.savings;

import com.fevertime.coinvillage.domain.account.SavingsSetting;
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

    private Long maturity;

    public SavingsSettingResponseDto(SavingsSetting savingsSetting) {
        this.settingsId = savingsSetting.getSettingsId();
        this.term = savingsSetting.getTerm();
        this.day = savingsSetting.getDay();
        this.bill = savingsSetting.getBill();
        this.tax = savingsSetting.getMember().getCountry().getTax();
        this.maturity = savingsSetting.getMaturity();
    }
}
