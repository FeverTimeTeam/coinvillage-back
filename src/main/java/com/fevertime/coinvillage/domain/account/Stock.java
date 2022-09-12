package com.fevertime.coinvillage.domain.account;

import com.fevertime.coinvillage.domain.BaseEntity;
import com.fevertime.coinvillage.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String content;

    private String description;

    private Long count;

    private Long price;

    private Long variable;

    private Long stockTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "stock", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<StockHistory> stockHistoryList;

    public void update(String content, String description, Long price, Long variable) {
        this.content = content;
        this.description = description;
        this.price = price;
        this.variable = variable;
    }
}
