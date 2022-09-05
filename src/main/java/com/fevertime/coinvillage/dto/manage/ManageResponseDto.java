package com.fevertime.coinvillage.dto.manage;

import com.fevertime.coinvillage.domain.Member;
import lombok.*;

import java.util.List;

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

    private List<String> jobList;

    public ManageResponseDto(Member member) {
        this.nickname = member.getNickname();

        if (member.getJob() == null) {
            this.jobName = "직업을 선택해주세요";
        } else {
            this.jobName = member.getJob().getJobName();
        }

        if (member.getJob() == null) {
            this.jobContent = "직업을 선택해주세요";
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
