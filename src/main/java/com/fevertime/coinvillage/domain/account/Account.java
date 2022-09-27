package com.fevertime.coinvillage.domain.account;

import com.fevertime.coinvillage.domain.BaseEntity;
import com.fevertime.coinvillage.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private Long accountTotal;

    @OneToOne
    private Member member;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<AccountHistory> accountHistoryList;

    public void changeAccountTotal(Long accountTotal) {
        this.accountTotal = accountTotal;
    }
}
