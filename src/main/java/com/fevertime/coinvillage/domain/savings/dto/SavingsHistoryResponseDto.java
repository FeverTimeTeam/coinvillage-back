package com.fevertime.coinvillage.domain.savings.dto;

import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.domain.savings.entity.SavingsHistory;
import lombok.*;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavingsHistoryResponseDto {
    private Long savingsId;

    private Long savingsTotal;

    private String createdAt;

    private String content;

    private String total;

    private Long interest;

    private StateName stateName;

    private Long maturity;

    public SavingsHistoryResponseDto(SavingsHistory savingsHistory) {
        this.savingsId = savingsHistory.getSavingsHistoryId();
        this.savingsTotal = savingsHistory.getSavings().getSavingsTotal();
        this.createdAt = savingsHistory.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("MM.dd"));
        this.content = savingsHistory.getContent();
        if (savingsHistory.getStateName() == StateName.DEPOSIT) {
            this.total = "+" + savingsHistory.getTotal();
        } else if (savingsHistory.getStateName() == StateName.WITHDRAWL) {
            this.total = "-" + savingsTotal;
        }
        this.interest = savingsHistory.getSavings().getSavingsSetting().getInterest();
        this.stateName = savingsHistory.getStateName();
        this.maturity = savingsHistory.getSavings().getSavingsSetting().getMaturity();
    }
}
