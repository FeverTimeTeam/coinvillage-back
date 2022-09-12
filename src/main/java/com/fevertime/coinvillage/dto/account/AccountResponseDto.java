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
    private Long accountId;

    private Long property;

    private String createdAt;

    private String content;

    private Long count;

    private String total;

    private String state;

    private Long accountTotal;

    public AccountResponseDto(Account account) {
        LocalDate localDate = LocalDate.parse(account.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).minusMonths(1);
        String payMonth = localDate.toString().substring(5, 7);

        this.accountId = account.getAccountId();
        this.property = account.getMember().getProperty();
        this.createdAt = account.getCreatedAt().format(DateTimeFormatter.ofPattern("MM.dd"));
        this.count = account.getCount();
        if (account.getStateName() == DEPOSIT && account.getContent().equals("월급")) {
            this.content = account.getContent() + '(' + payMonth  + "월)";
            this.total = ("+" + account.getTotal());
        } else if (account.getStateName() == WITHDRAWL) {
            this.content = account.getContent();
            this.total = ("-" + account.getTotal());
        }
        this.state = account.getStateName().toString();
        this.accountTotal = account.getMember().getAccountTotal();
    }
}
