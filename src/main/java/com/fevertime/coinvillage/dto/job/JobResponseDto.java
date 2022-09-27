package com.fevertime.coinvillage.dto.job;

import com.fevertime.coinvillage.domain.job.Job;
import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.dto.login.MemberResponseDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobResponseDto {
    private Long jobId;

    private String jobName;

    private String jobContent;

    private int headcount;

    private Long payCheck;

    public JobResponseDto(Job job) {
        this.jobId = job.getJobId();
        this.jobName = job.getJobName();
        this.jobContent = job.getJobContent();
        this.headcount = 1;
        this.payCheck = job.getPayCheck();
    }
}
