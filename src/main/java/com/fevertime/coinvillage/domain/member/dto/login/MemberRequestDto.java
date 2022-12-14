package com.fevertime.coinvillage.domain.member.dto.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fevertime.coinvillage.domain.member.entity.Authority;
import com.fevertime.coinvillage.domain.country.entity.Country;
import com.fevertime.coinvillage.domain.job.entity.Job;
import com.fevertime.coinvillage.domain.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
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

    private Long property;

    private String countryName;

    @ApiModelProperty(hidden = true)
    private Country country;

    @ApiModelProperty(hidden = true)
    private Job job;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(nickname)
                .nickname(nickname)
                .authorities(authority)
                .phoneNumber(phoneNumber)
                .property(property)
                .country(country)
                .job(job)
                .build();
    }

    public Country toCountry() {
        return Country.builder()
                .countryName(countryName)
                .tax(0L)
                .build();
    }
}
