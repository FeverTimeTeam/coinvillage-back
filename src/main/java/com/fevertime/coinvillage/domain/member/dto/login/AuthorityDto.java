package com.fevertime.coinvillage.domain.member.dto.login;

import com.fevertime.coinvillage.domain.model.Role;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityDto {
    private Role authorityName;
}
