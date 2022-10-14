package com.fevertime.coinvillage.domain.stock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class CurrentStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long currentStockId;

    private String content;

    private String description;

    private Long count;

    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Stock stock;

    public void change(String content, String description, Long price) {
        this.content = content;
        this.description = description;
        this.price = price;
    }

    public void changeCount(Long count) {
        this.count -= count;
    }

    public void buyCount(Long count) {
        this.count += count;
    }
}
