package com.fevertime.coinvillage.dto.job;

import com.fevertime.coinvillage.domain.Job;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobRequestDto {
    private String jobName;

    private String jobContent;

    private Long payCheck;

    public Job toEntity() {
        return Job.builder()
                .jobName(jobName)
                .jobContent(jobContent)
                .payCheck(payCheck)
                .build();
    }
}
