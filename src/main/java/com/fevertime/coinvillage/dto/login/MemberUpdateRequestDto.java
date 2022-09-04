package com.fevertime.coinvillage.dto.login;

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
