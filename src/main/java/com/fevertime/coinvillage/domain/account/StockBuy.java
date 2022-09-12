package com.fevertime.coinvillage.domain.account;

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
public class StockBuy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockBuyId;

    private String content;

    @Enumerated(EnumType.STRING)
    private StateName stateName;

    private Long count;

    private Long total;

    @ManyToOne(fetch = FetchType.LAZY)
    private Stock stock;
}
