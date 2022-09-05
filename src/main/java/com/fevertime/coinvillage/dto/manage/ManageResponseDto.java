package com.fevertime.coinvillage.dto.manage;

import com.fevertime.coinvillage.domain.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManageResponseDto {
    private Long rank;

    private String jobName;

    private String jobContent;

    // 월급
    private Long payCheck;

    // 재산
    private Long property;

    public ManageResponseDto(Member member) {
        this.jobName = member.getJob().getJobName();
        this.jobContent = member.getJob().getJobName();
        this.payCheck = member.getJob().getPayCheck();
        this.property = member.getProperty();
    }
}
