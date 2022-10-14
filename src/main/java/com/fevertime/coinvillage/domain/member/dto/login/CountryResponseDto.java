package com.fevertime.coinvillage.domain.member.dto.login;

import com.fevertime.coinvillage.domain.country.entity.Country;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryResponseDto {
    private Long countryId;

    private String countryName;

    private List<MemberResponseDto> memberResponseDtoList;

    public CountryResponseDto(Country country) {
        this.countryId = country.getCountryId();
        this.countryName = country.getCountryName();
        this.memberResponseDtoList = country.getMemberList().stream().map(MemberResponseDto::new).collect(Collectors.toList());
    }
}
