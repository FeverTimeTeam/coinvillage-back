package com.fevertime.coinvillage.service;

import com.fevertime.coinvillage.domain.job.Job;
import com.fevertime.coinvillage.dto.job.JobRequestDto;
import com.fevertime.coinvillage.dto.job.JobResponseDto;
import com.fevertime.coinvillage.dto.job.JobUpdateRequestDto;
import com.fevertime.coinvillage.repository.JobRepository;
import com.fevertime.coinvillage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final MemberRepository memberRepository;

    // 직업 추가
    @Transactional
    public JobResponseDto addJob(JobRequestDto jobRequestDto, String email) {
        jobRequestDto.setCountry(memberRepository.findByEmail(email).getCountry());
        Job job = jobRequestDto.toEntity();
        jobRepository.save(job);
        return JobResponseDto.builder()
                .jobName(jobRequestDto.getJobName())
                .jobContent(jobRequestDto.getJobContent())
                .headcount(jobRepository.countByJobName(jobRequestDto.getJobName()) - 1)
                .payCheck(jobRequestDto.getPayCheck())
                .memberList(null)
                .build();
    }

    // 직업 전체 보기
    @Transactional
    public List<JobResponseDto> viewJobs(String email) {
        List<Job> jobs = jobRepository.findAllByCountry_CountryName(memberRepository.findByEmail(email).getCountry().getCountryName());
        return jobs.stream().map(JobResponseDto::new)
                .collect(Collectors.toList());
    }

    // 직업 삭제
    @Transactional
    public void deletejob(Long jobId) {
        jobRepository.deleteById(jobId);
    }

    // 직업 수정
    @Transactional
    public JobResponseDto modjob(Long jobId, JobUpdateRequestDto jobUpdateRequestDto) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new IllegalArgumentException("없는 직업입니다."));

        job.update(jobUpdateRequestDto.getJobContent(), jobUpdateRequestDto.getPayCheck());

        jobRepository.save(job);

        return new JobResponseDto(job);
    }
}
