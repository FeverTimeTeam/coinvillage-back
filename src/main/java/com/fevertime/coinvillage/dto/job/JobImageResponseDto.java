package com.fevertime.coinvillage.dto.job;

import com.fevertime.coinvillage.domain.job.JobImage;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobImageResponseDto {
    private Long jobImageId;

    private String jobImageUrl;

    public JobImageResponseDto(JobImage jobImage) {
        this.jobImageId = jobImage.getJobImageId();
        this.jobImageUrl = jobImage.getJobImageUrl();
    }
}
