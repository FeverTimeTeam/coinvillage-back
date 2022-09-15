package com.fevertime.coinvillage.domain.job;

import com.fevertime.coinvillage.domain.country.Country;
import com.fevertime.coinvillage.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;

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

    @OneToOne
    private Country country;

    @OneToMany(mappedBy = "job", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @BatchSize(size = 10)
    private List<Member> memberList;

    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @BatchSize(size = 10)
    private List<JobImage> jobImageList;

    public void update(String jobContent, Long payCheck) {
        this.jobContent = jobContent;
        this.payCheck = payCheck;
    }
}
