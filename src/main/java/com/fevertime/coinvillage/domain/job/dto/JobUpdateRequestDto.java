package com.fevertime.coinvillage.domain.job.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobUpdateRequestDto {
    private String jobContent;

    private Long payCheck;
}
