package com.fevertime.coinvillage.dto.savings;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavingsReceiveResponseDto {
    private Long savingsId;

    private Long savingsTotal;

    private String createdAt;

    private String content;

    private String total;

    private Long a;
}
