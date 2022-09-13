package com.fevertime.coinvillage.dto.job;

import com.fevertime.coinvillage.domain.job.Job;
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

    private List<MemberResponseDto> memberList;

    private List<JobImageResponseDto> jobImageList;

    public JobResponseDto(Job job) {
        this.jobId = job.getJobId();
        this.jobName = job.getJobName();
        this.jobContent = job.getJobContent();
        this.headcount = job.getMemberList().size();
        this.payCheck = job.getPayCheck();
        this.memberList = job.getMemberList().stream().map(MemberResponseDto::new).collect(Collectors.toList());
        this.jobImageList = job.getJobImageList().stream()
                .map(JobImageResponseDto::new)
                .collect(Collectors.toList());
    }
}
