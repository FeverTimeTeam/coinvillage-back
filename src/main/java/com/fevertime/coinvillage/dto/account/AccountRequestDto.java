package com.fevertime.coinvillage.dto.account;

import com.fevertime.coinvillage.domain.account.Account;
import com.fevertime.coinvillage.domain.model.StateName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequestDto {
    private String content;

    private Long count;

    private Long total;

    private StateName stateName;

    public Account toEntity() {
        return Account.builder()
                .content(content)
                .count(count)
                .total(total)
                .stateName(stateName)
                .build();
    }
}
