package com.fevertime.coinvillage.dto.login;

import com.fevertime.coinvillage.domain.Role;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityDto {
    private Role authorityName;
}
