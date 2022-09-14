package com.fevertime.coinvillage.domain.account;

import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.domain.model.Term;
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

    private String week;

    private String day;

    private Long bill;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Member member;

    public void updateBill(Long bill) {
        this.bill = bill;
    }

    public void updateDay(String day) {
        this.day = day;
    }
}
