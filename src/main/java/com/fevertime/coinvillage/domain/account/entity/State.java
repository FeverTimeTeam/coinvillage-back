package com.fevertime.coinvillage.domain.account.entity;

import com.fevertime.coinvillage.domain.model.StateName;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class State {
    @Id
    @Enumerated(EnumType.STRING)
    private StateName stateName;
}
