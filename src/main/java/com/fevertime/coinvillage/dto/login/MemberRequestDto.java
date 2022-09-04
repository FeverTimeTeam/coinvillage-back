package com.fevertime.coinvillage.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fevertime.coinvillage.domain.Authority;
import com.fevertime.coinvillage.domain.Country;
import com.fevertime.coinvillage.domain.Job;
import com.fevertime.coinvillage.domain.Member;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {
    @NotNull
    @Size(min = 3, max = 50)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;

    private Set<Authority> authority;

    private String phoneNumber;

    private Long currentMoney;

    private String countryName;

    private Country country;

    private Job job;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(nickname)
                .nickname(nickname)
                .authorities(authority)
                .phoneNumber(phoneNumber)
                .currentMoney(currentMoney)
                .country(country)
                .job(job)
                .build();
    }

    public Country toCountry() {
        return Country.builder()
                .countryName(countryName)
                .build();
    }
}
