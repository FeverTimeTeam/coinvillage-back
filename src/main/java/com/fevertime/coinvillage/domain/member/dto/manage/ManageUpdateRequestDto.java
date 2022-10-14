package com.fevertime.coinvillage.domain.member.dto.manage;

import com.fevertime.coinvillage.domain.job.entity.Job;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManageUpdateRequestDto {
    private Job job;
}
