package com.fevertime.coinvillage.service;

import com.fevertime.coinvillage.domain.Job;
import com.fevertime.coinvillage.dto.job.JobRequestDto;
import com.fevertime.coinvillage.dto.job.JobResponseDto;
import com.fevertime.coinvillage.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;

    // 직업 추가
    @Transactional
    public JobResponseDto addJob(JobRequestDto jobRequestDto) {
        Job job = jobRequestDto.toEntity();
        jobRepository.save(job);
        return JobResponseDto.builder()
                .jobName(jobRequestDto.getJobName())
                .jobContent(jobRequestDto.getJobContent())
                .headcount(jobRepository.countByJobName(jobRequestDto.getJobName()) - 1)
                .salary(jobRequestDto.getSalary())
                .memberList(null)
                .build();
    }

    // 직업 전체 보기
    @Transactional
    public List<JobResponseDto> viewJobs() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream().map(JobResponseDto::new)
                .collect(Collectors.toList());
    }

    // 직업 삭제
    @Transactional
    public void deletejob(Long jobId) {
        jobRepository.deleteById(jobId);
    }
}
