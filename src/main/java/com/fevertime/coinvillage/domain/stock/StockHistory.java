package com.fevertime.coinvillage.domain.stock;

import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.domain.stock.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockHistoryId;

    private String content;

    private Long count;

    private Long price;

    @Enumerated(EnumType.STRING)
    private StateName stateName;

    @ManyToOne(fetch = FetchType.LAZY)
    private StockBuy stockBuy;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
