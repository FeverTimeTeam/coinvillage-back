package com.fevertime.coinvillage.dto.manage;

import com.fevertime.coinvillage.domain.account.Account;
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

        if (member.getJob() == null) {
            this.jobName = "무직";
        } else {
            this.jobName = member.getJob().getJobName();
        }

        if (member.getJob() == null) {
            this.jobContent = "없음";
        } else {
            this.jobContent = member.getJob().getJobContent();
        }

        if (member.getJob() == null) {
            this.payCheck = 0L;
        } else {
            this.payCheck = member.getJob().getPayCheck();
        }

        this.property = member.getProperty();
    }
}
