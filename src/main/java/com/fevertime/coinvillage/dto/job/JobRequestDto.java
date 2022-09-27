package com.fevertime.coinvillage.dto.job;

import com.fevertime.coinvillage.domain.country.Country;
import com.fevertime.coinvillage.domain.job.Job;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(hidden = true)
    private Country country;

    public Job toEntity() {
        return Job.builder()
                .jobName(jobName)
                .jobContent(jobContent)
                .payCheck(payCheck)
                .country(country)
                .build();
    }
}
