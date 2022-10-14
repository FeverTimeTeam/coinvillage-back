package com.fevertime.coinvillage.domain.job;

import com.fevertime.coinvillage.domain.job.entity.Job;
import com.fevertime.coinvillage.domain.job.dto.JobRequestDto;
import com.fevertime.coinvillage.domain.job.dto.JobResponseDto;
import com.fevertime.coinvillage.domain.job.dto.JobUpdateRequestDto;
import com.fevertime.coinvillage.domain.job.repository.JobRepository;
import com.fevertime.coinvillage.domain.member.repository.MemberRepository;
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
    public JobResponseDto makeJob(JobRequestDto jobRequestDto, String email) {
        jobRequestDto.setCountry(memberRepository.findByEmail(email).getCountry());
        Job job = jobRequestDto.toEntity();
        jobRepository.save(job);
        return JobResponseDto.builder()
                .jobName(jobRequestDto.getJobName())
                .jobContent(jobRequestDto.getJobContent())
                .headcount(1)
                .payCheck(jobRequestDto.getPayCheck())
                .build();
    }

    // 직업 전체 보기
    @Transactional(readOnly = true)
    public List<JobResponseDto> showJobs(String email) {
        List<Job> jobs = jobRepository.findAllByCountry_CountryName(memberRepository.findByEmail(email).getCountry().getCountryName());
        return jobs.stream().map(JobResponseDto::new)
                .collect(Collectors.toList());
    }

    // 직업 삭제
    @Transactional
    public void deljob(Long jobId) {
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
