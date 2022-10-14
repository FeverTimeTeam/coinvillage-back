package com.fevertime.coinvillage.domain.job.entity;

import com.fevertime.coinvillage.domain.country.entity.Country;
import com.fevertime.coinvillage.domain.member.entity.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    private String jobName;

    private String jobContent;

    private Long payCheck;

    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    @OneToOne(mappedBy = "job")
    private Member member;

    public void update(String jobContent, Long payCheck) {
        this.jobContent = jobContent;
        this.payCheck = payCheck;
    }
}
