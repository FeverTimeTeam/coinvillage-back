package com.fevertime.coinvillage.dto.login;

import com.fevertime.coinvillage.domain.member.Member;
import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    private Long memberId;

    private String email;

    private String password;

    private String nickname;

    private String phoneNumber;

    private Long property;

    private Set<AuthorityDto> authorityDtoSet;

    public MemberResponseDto(Member member) {
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
        this.phoneNumber = member.getPhoneNumber();
        this.property = member.getProperty();
        this.authorityDtoSet = member.getAuthorities().stream()
                .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                .collect(Collectors.toSet());
    }
}
