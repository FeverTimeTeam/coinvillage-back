package com.fevertime.coinvillage.domain.member;

import com.fevertime.coinvillage.domain.Country;
import com.fevertime.coinvillage.domain.Job.Job;
import com.fevertime.coinvillage.domain.account.Account;
import com.fevertime.coinvillage.domain.account.SavingsSetting;
import com.fevertime.coinvillage.domain.account.Stock;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String email;

    private String password;

    private String nickname;

    private boolean activated;

    private String phoneNumber;

    private Long property;

    private Long accountTotal;

    private Long savingsTotal;

    private Long stockTotal;

    @ManyToMany
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "memberId")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authorityName")})
    private Set<Authority> authorities;

    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    private Job job;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Account> accountList;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Stock> stockList;

    @OneToOne(mappedBy = "member")
    private SavingsSetting savingsSetting;

    public void update(Job job) {
        this.job = job;
    }

    public void changeAccountTotal(Long accountTotal) {
        this.accountTotal = accountTotal;
    }

    public void changeSavingsTotal(Long savingsTotal) {
        this.savingsTotal = savingsTotal;
    }

    public void changeStockTotal(Long stockTotal) {
        this.stockTotal = stockTotal;
    }

    public void changeProperty(Long property) { this.property = property; }
}
