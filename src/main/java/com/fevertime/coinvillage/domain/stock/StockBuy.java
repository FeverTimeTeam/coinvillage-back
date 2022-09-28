package com.fevertime.coinvillage.domain.stock;

import com.fevertime.coinvillage.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@DynamicUpdate
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

    public void update(String content, String description, Long price) {
        this.content = content;
        this.description = description;
        this.price = price;
    }
}
