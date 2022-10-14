package com.fevertime.coinvillage.domain.member.dto.login;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberUpdateRequestDto {
    private List<String> memberList;
}
