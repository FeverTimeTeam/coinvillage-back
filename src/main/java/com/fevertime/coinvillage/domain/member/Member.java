package com.fevertime.coinvillage.domain.member;

import com.fevertime.coinvillage.domain.Country;
import com.fevertime.coinvillage.domain.Job;
import com.fevertime.coinvillage.domain.account.Account;
import com.fevertime.coinvillage.domain.member.Authority;
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

    public void update(Job job) {
        this.job = job;
    }

    public void plusPay(Long property) {
        this.property = property;
    }

    public void consume(Long total) { this.property -= total; }
}
