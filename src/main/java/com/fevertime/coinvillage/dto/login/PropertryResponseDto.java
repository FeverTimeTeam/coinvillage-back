package com.fevertime.coinvillage.dto.login;

import com.fevertime.coinvillage.domain.member.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertryResponseDto {
    private Long property;

    public PropertryResponseDto(Member member) {
        this.property = member.getProperty();
    }
}
