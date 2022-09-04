package com.fevertime.coinvillage.dto.job;

import com.fevertime.coinvillage.domain.Job;
import com.fevertime.coinvillage.dto.login.MemberResponseDto;
import com.fevertime.coinvillage.repository.JobRepository;
import lombok.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobResponseDto {
    private Long jobId;

    private String jobName;

    private String jobContent;

    private int headcount;

    private int salary;

    private List<MemberResponseDto> memberList;

    public JobResponseDto(Job job) {
        this.jobId = job.getJobId();
        this.jobName = job.getJobName();
        this.jobContent = job.getJobContent();
        this.headcount = job.getMemberList().size();
        this.salary = job.getSalary();
        this.memberList = job.getMemberList().stream().map(MemberResponseDto::new).collect(Collectors.toList());
    }
}
