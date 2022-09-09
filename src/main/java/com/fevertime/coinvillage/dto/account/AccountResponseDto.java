package com.fevertime.coinvillage.dto.account;

import com.fevertime.coinvillage.domain.account.Account;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.fevertime.coinvillage.domain.model.StateName.DEPOSIT;
import static com.fevertime.coinvillage.domain.model.StateName.WITHDRAWL;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponseDto {
    private Long property;

    private String createdAt;

    private String content;

    private Long count;

    private String total;

    public AccountResponseDto(Account account) {
        LocalDate localDate = LocalDate.parse(account.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).minusMonths(1);
        String payMonth = localDate.toString().substring(5, 7);

        this.property = account.getMember().getProperty();
        this.createdAt = account.getCreatedAt().format(DateTimeFormatter.ofPattern("MM.dd"));
        this.content = account.getContent() + '(' + payMonth  + "월)";
        this.count = account.getCount();
        if (account.getStateName() == DEPOSIT) {
            this.total = ("+" + account.getTotal());
        } else if (account.getStateName() == WITHDRAWL) {
            this.total = ("-" + account.getTotal());
        }
    }
}
