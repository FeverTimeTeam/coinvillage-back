package com.fevertime.coinvillage.controller.api;

import com.fevertime.coinvillage.dto.job.JobRequestDto;
import com.fevertime.coinvillage.dto.job.JobResponseDto;
import com.fevertime.coinvillage.dto.job.JobUpdateRequestDto;
import com.fevertime.coinvillage.service.JobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("jobs")
@Api(tags = "직업관리")
public class JobApiController {
    private final JobService jobService;
    
    @PostMapping
    @PreAuthorize("hasRole('ROLE_RULER')")
    @ApiOperation(value = "직업 추가")
    public ResponseEntity<JobResponseDto> addJob(@RequestBody JobRequestDto jobRequestDto, Authentication authentication) {
        return ResponseEntity.ok(jobService.addJob(jobRequestDto, authentication.getName()));
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_RULER')")
    @ApiOperation(value = "직업 전체 보기")
    public ResponseEntity<List<JobResponseDto>> viewJobs(Authentication authentication) {
        return ResponseEntity.ok(jobService.viewJobs(authentication.getName()));
    }

    @DeleteMapping("{jobId}")
    @PreAuthorize("hasRole('ROLE_RULER')")
    @ApiOperation(value = "직업 삭제")
    public ResponseEntity removeJob(@PathVariable Long jobId) {
        jobService.deletejob(jobId);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("{jobId}")
    @PreAuthorize("hasRole('ROLE_RULER')")
    @ApiOperation(value = "직업 수정")
    public ResponseEntity<JobResponseDto> modJob(@PathVariable Long jobId, @RequestBody JobUpdateRequestDto jobUpdateRequestDto) {
        return ResponseEntity.ok(jobService.modjob(jobId, jobUpdateRequestDto));
    }
}


