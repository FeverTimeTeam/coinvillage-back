package com.fevertime.coinvillage.dto.savings;

import com.fevertime.coinvillage.domain.account.Savings;
import com.fevertime.coinvillage.domain.model.StateName;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class SavingsResponseDto {
    private Long savingsId;

    private Long savingsTotal;

    private String createdAt;

    private String content;

    private String total;

    private Long interest;

    private StateName stateName;

    private Long maturity;

    public SavingsResponseDto(Savings savings) {
        this.savingsId = savings.getSavingsId();
        this.savingsTotal = savings.getSavingsTotal();
        this.createdAt = savings.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("MM.dd"));
        this.content = savings.getContent();
        if (savings.getStateName() == StateName.DEPOSIT) {
            this.total = "+" + savings.getTotal();
        } else if (savings.getStateName() == StateName.WITHDRAWL) {
            this.total = "-" + savingsTotal;
        }
        this.interest = savings.getAccount().getMember().getSavingsSetting().getInterest();
        this.stateName = savings.getStateName();
        this.maturity = savings.getAccount().getMember().getSavingsSetting().getMaturity();
    }
}
