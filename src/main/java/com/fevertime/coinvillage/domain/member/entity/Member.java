package com.fevertime.coinvillage.domain.member.entity;

import com.fevertime.coinvillage.domain.account.entity.Account;
import com.fevertime.coinvillage.domain.country.entity.Country;
import com.fevertime.coinvillage.domain.job.entity.Job;
import com.fevertime.coinvillage.domain.savings.entity.Savings;
import com.fevertime.coinvillage.domain.stock.entity.Stock;
import com.fevertime.coinvillage.domain.stock.entity.StockHistory;
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

    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "memberId")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authorityName")})
    private Set<Authority> authorities;

    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    @OneToOne
    private Job job;

    @OneToOne(mappedBy = "member")
    private Account account;

    @OneToOne(mappedBy = "member")
    private Savings savings;

    @OneToOne(mappedBy = "member")
    private Stock stock;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<StockHistory> stockHistoryList;

    public void update(Job job) {
        this.job = job;
    }

    public void changeProperty(Long property) { this.property = property; }

    public void changeProfileImg(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
