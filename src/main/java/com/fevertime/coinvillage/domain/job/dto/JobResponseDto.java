package com.fevertime.coinvillage.domain.job.dto;

import com.fevertime.coinvillage.domain.job.entity.Job;
import lombok.*;

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
