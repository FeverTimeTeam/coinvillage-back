package com.fevertime.coinvillage.domain.Job;

import com.fevertime.coinvillage.domain.Country;
import com.fevertime.coinvillage.domain.member.Member;
import lombok.*;

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
    private List<Member> memberList;

    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<JobImage> jobImageList;

    public void update(String jobContent, Long payCheck) {
        this.jobContent = jobContent;
        this.payCheck = payCheck;
    }
}
