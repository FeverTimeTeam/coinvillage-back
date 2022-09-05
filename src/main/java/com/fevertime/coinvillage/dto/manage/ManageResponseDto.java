package com.fevertime.coinvillage.dto.manage;

import com.fevertime.coinvillage.domain.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManageResponseDto {
    private String nickname;

    private String jobName;

    private String jobContent;

    // 월급
    private Long payCheck;

    // 재산
    private Long property;

    public ManageResponseDto(Member member) {
        this.nickname = member.getNickname();
        this.property = member.getProperty();
    }
}
