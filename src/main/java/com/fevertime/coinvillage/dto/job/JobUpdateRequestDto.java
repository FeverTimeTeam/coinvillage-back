package com.fevertime.coinvillage.dto.job;

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
