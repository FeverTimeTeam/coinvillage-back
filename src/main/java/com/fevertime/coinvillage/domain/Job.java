package com.fevertime.coinvillage.domain;

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

    @OneToMany(mappedBy = "job", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Member> memberList;

    public void update(String jobContent, Long payCheck) {
        this.jobContent = jobContent;
        this.payCheck = payCheck;
    }
}
