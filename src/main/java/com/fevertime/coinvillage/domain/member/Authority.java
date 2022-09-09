package com.fevertime.coinvillage.domain.member;

import com.fevertime.coinvillage.domain.model.Role;
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
