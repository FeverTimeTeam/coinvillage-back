package com.fevertime.coinvillage.domain.savings;

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
public class Savings extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savingsId;

    private Long savingsTotal;

    @OneToOne
    private Member member;

    @OneToOne(mappedBy = "savings")
    private SavingsSetting savingsSetting;

    @OneToMany(mappedBy = "savings", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<SavingsHistory> savingsHistoryList;

    public void changeSavingsTotal(Long savingsTotal) {
        this.savingsTotal = savingsTotal;
    }
}
