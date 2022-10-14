package com.fevertime.coinvillage.domain.member.dto.login;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    // accessToken
    private String token;

    private MemberResponseDto memberResponseDto;
}
