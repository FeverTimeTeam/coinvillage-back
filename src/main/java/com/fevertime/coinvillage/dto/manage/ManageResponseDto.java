package com.fevertime.coinvillage.dto.manage;

import com.fevertime.coinvillage.domain.member.Member;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManageResponseDto {
    private Long memberId;

    private String nickname;

    private String jobName;

    private String jobContent;

    // 월급
    private Long payCheck;

    // 재산
    private Long property;

    private List<String> jobList;

    public ManageResponseDto(Member member) {
        this.memberId = member.getMemberId();
        this.nickname = member.getNickname();
        this.jobName = (member.getJob() == null) ? "무직" : member.getJob().getJobName();
        this.jobContent = (member.getJob() == null) ? "없음" : member.getJob().getJobContent();
        this.payCheck = (member.getJob() == null) ? 0L : member.getJob().getPayCheck();
        this.property = member.getProperty();
    }
}
