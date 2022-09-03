package com.fevertime.coinvillage.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authority {

    @Id
    @Enumerated(EnumType.STRING)
    private Role authorityName;
}
