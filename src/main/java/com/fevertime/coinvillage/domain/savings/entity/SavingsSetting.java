package com.fevertime.coinvillage.domain.savings.entity;

import com.fevertime.coinvillage.domain.model.Term;
import com.fevertime.coinvillage.domain.savings.entity.Savings;
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
public class SavingsSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long settingsId;

    @Enumerated(EnumType.STRING)
    private Term term;

    private String day;

    private Long bill;

    private Long maturity;

    private Long interest;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Savings savings;

    public void updateBill(Long bill) {
        this.bill = bill;
    }

    public void updateDay(String day) {
        this.day = day;
    }

    public void updateInterest(Long interest) {
        this.interest = interest;
    }

    public void updateMaturity() {
        if (maturity == 0) {
            this.maturity = 0L;
            this.bill = 0L;
        } else {
            this.maturity -= 1;
        }
    }
}
