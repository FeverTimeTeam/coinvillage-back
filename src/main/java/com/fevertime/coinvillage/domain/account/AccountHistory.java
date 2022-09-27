package com.fevertime.coinvillage.domain.account;

import com.fevertime.coinvillage.domain.BaseEntity;
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
public class AccountHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountHistoryId;

    private String content;

    private Long count;

    private Long total;

    @Enumerated(EnumType.STRING)
    private StateName stateName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
}
