package com.fevertime.coinvillage.domain.stock;

import com.fevertime.coinvillage.domain.BaseEntity;
import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.domain.stock.Stock;
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
public class StockBuy extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockBuyId;

    private String content;

    private String description;

    private Long count;

    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Stock stock;

    @OneToMany(mappedBy = "stockBuy", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<StockHistory> stockHistoryList;

    public void clear() {
        this.count = 0L;
    }

    public void update(String content, String description, Long price) {
        this.content = content;
        this.description = description;
        this.price = price;
    }
}
