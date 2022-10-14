package com.fevertime.coinvillage.domain.account.dto;

import com.fevertime.coinvillage.domain.account.entity.Account;
import com.fevertime.coinvillage.domain.account.entity.AccountHistory;
import com.fevertime.coinvillage.domain.model.StateName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountHistoryRequestDto {
    private String content;

    private Long count;

    private Long total;

    @ApiModelProperty(hidden = true)
    private Long accountTotal;

    @ApiModelProperty(hidden = true)
    private StateName stateName;

    @ApiModelProperty(hidden = true)
    private Account account;

    public AccountHistory toEntity() {
        return AccountHistory.builder()
                .content(content)
                .count(count)
                .total(total)
                .stateName(stateName)
                .account(account)
                .build();
    }
}
