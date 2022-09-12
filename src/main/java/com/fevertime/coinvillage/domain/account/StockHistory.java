package com.fevertime.coinvillage.domain.account;

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

    private Long variable;

    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Stock stock;
}
