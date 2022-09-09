package com.fevertime.coinvillage.domain.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Savings extends Account {
    private String term;

    private String week;

    private Long day;
}
