package com.fevertime.coinvillage.dto.savings;

import com.fevertime.coinvillage.domain.account.Savings;
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

    public SavingsResponseDto(Savings savings) {
        this.savingsId = savings.getSavingsId();
        this.savingsTotal = savings.getSavingsTotal();
        this.createdAt = savings.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("MM.dd"));
        this.content = savings.getContent();
        this.total = "+" + savings.getTotal();
    }
}
