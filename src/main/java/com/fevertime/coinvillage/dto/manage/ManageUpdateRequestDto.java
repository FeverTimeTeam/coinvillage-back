package com.fevertime.coinvillage.dto.manage;

import com.fevertime.coinvillage.domain.Job;
import com.fevertime.coinvillage.domain.Member;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManageUpdateRequestDto {
    private String nickname;

    private Job job;

    public Member toEntity() {
        return Member.builder()
                .nickname(nickname)
                .job(job)
                .build();
    }
}
