package com.fevertime.coinvillage.controller.api;

import com.fevertime.coinvillage.dto.job.JobRequestDto;
import com.fevertime.coinvillage.dto.job.JobResponseDto;
import com.fevertime.coinvillage.dto.login.MemberUpdateRequestDto;
import com.fevertime.coinvillage.service.JobService;
import com.fevertime.coinvillage.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("job")
@Api(tags = "직업관리")
public class JobApiController {
    private final JobService jobService;
    private final MemberService memberService;
    
    @PostMapping
    @ApiOperation(value = "직업 추가")
    public ResponseEntity<JobResponseDto> addJob(@RequestBody JobRequestDto jobRequestDto) {
        return ResponseEntity.ok(jobService.addJob(jobRequestDto));
    }

    @GetMapping
    @ApiOperation(value = "직업 전체 보기")
    public ResponseEntity<List<JobResponseDto>> viewJobs() {
        return ResponseEntity.ok(jobService.viewJobs());
    }

    @PutMapping("{jobId}")
    @ApiOperation(value = "이거 쓰지 마세요")
    public ResponseEntity<Long> modJob(@PathVariable Long jobId, @RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {
        return ResponseEntity.ok(memberService.modJob(jobId, memberUpdateRequestDto));
    }

    @DeleteMapping("{jobId}")
    @ApiOperation(value = "직업 삭제")
    public ResponseEntity removeJob(@PathVariable Long jobId) {
        jobService.deletejob(jobId);
        return ResponseEntity.noContent().build();
    }
}
