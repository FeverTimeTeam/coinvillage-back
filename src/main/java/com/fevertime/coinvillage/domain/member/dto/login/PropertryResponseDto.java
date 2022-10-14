package com.fevertime.coinvillage.domain.member.dto.login;

import com.fevertime.coinvillage.domain.member.entity.Member;
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
