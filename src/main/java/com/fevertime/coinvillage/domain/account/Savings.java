package com.fevertime.coinvillage.domain.account;

import com.fevertime.coinvillage.domain.BaseEntity;
import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.domain.model.Term;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Savings extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savingsId;

    private String content;

    private Long total;

    private Long savingsTotal;

    @Enumerated(EnumType.STRING)
    private StateName stateName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public void changeSavingsTotal(Long total) {
        this.savingsTotal += total;
    }
}
