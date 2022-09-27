package com.fevertime.coinvillage.dto.account;

import com.fevertime.coinvillage.domain.account.AccountHistory;
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
public class AccountHistoryResponseDto {
    private Long accountId;

    private Long property;

    private String createdAt;

    private String content;

    private Long count;

    private String total;

    private String state;

    private Long accountTotal;

    public AccountHistoryResponseDto(AccountHistory accountHistory) {
        LocalDate localDate = LocalDate.parse(accountHistory.getCreatedAt()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).minusMonths(1);
        String payMonth = localDate.toString().substring(5, 7);

        this.accountId = accountHistory.getAccountHistoryId();
        this.property = accountHistory.getAccount().getMember().getProperty();
        this.createdAt = accountHistory.getCreatedAt().format(DateTimeFormatter.ofPattern("MM.dd"));
        this.count = accountHistory.getCount();

        if (accountHistory.getStateName() == DEPOSIT && accountHistory.getContent().equals("월급")) {
            this.content = accountHistory.getContent() + '(' + payMonth  + "월)";
            this.total = ("+" + accountHistory.getTotal());
        } else if (accountHistory.getStateName() == DEPOSIT && accountHistory.getContent().equals("주식통장에서 입금")) {
            this.content = accountHistory.getContent();
            this.total = ("+" + accountHistory.getTotal());
        } else if (accountHistory.getStateName() == WITHDRAWL) {
            this.content = accountHistory.getContent();
            this.total = ("-" + accountHistory.getTotal());
        } else if (accountHistory.getStateName() == DEPOSIT && accountHistory.getContent().equals("적금 만기")) {
            this.content = accountHistory.getContent();
            this.total = ("+" + accountHistory.getTotal());
        } else if (accountHistory.getStateName() == WITHDRAWL && accountHistory.getContent().equals("주식 통장으로 입금")) {
            this.content = accountHistory.getContent();
            this.total = ("-" + accountHistory.getTotal());
        }
        this.state = accountHistory.getStateName().toString();
        this.accountTotal = accountHistory.getAccount().getAccountTotal();
    }
}
