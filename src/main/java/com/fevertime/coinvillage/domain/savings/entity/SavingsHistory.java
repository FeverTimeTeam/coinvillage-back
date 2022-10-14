package com.fevertime.coinvillage.domain.savings.entity;

import com.fevertime.coinvillage.domain.savings.entity.Savings;
import com.fevertime.coinvillage.global.config.entity.BaseEntity;
import com.fevertime.coinvillage.domain.model.StateName;
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
public class SavingsHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savingsHistoryId;

    private String content;

    private Long total;

    @Enumerated(EnumType.STRING)
    private StateName stateName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Savings savings;
}
