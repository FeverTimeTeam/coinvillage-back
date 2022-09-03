package com.fevertime.coinvillage.dto.login;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    // accessToken
    private String token;
}
