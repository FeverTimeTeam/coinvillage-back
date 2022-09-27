package com.fevertime.coinvillage.domain.stock;

import com.fevertime.coinvillage.domain.BaseEntity;
import com.fevertime.coinvillage.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Stock extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;

    private Long stockTotal;

    @OneToOne
    private Member member;

    @OneToMany(mappedBy = "stock", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @BatchSize(size = 10)
    private List<StockBuy> stockBuyList;

    @OneToMany(mappedBy = "stock", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @BatchSize(size = 10)
    private List<CurrentStock> currentStockList;

    public void changeStockTotal(Long stockTotal) {
        this.stockTotal = stockTotal;
    }
}
