package com.fevertime.coinvillage.domain.account;

import com.fevertime.coinvillage.domain.BaseEntity;
import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.domain.model.StateName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

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

    private String content;

    private Long count;

    private Long total;

    @Enumerated(EnumType.STRING)
    private StateName stateName;

    private Long accountTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @BatchSize(size = 10)
    private List<Savings> savingsList;
}
