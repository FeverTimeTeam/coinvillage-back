package com.fevertime.coinvillage.dto.savings;

import com.fevertime.coinvillage.domain.account.Account;
import com.fevertime.coinvillage.domain.account.Savings;
import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.domain.model.Term;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SavingsRequestDto {

    private StateName stateName;

    private Long savingsTotal;

    private Long total;

    private String content;

    private Account account;
}
