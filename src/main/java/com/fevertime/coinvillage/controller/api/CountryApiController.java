package com.fevertime.coinvillage.controller.api;

import com.fevertime.coinvillage.dto.country.TodayMessageRequestDto;
import com.fevertime.coinvillage.dto.country.TodayMessageResponseDto;
import com.fevertime.coinvillage.service.TodayMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = "오늘의 정보")
@RequestMapping("infos")
public class CountryApiController {
    private final TodayMessageService todayMessageService;

    @GetMapping
    @ApiOperation("오늘의 정보 조회")
    public ResponseEntity<TodayMessageResponseDto> showInfo(Authentication authentication) {
        return ResponseEntity.ok(todayMessageService.showInfo(authentication.getName()));
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_RULER')")
    @ApiOperation("오늘의 정보 수정")
    public ResponseEntity<TodayMessageResponseDto> makeInfo(Authentication authentication, @RequestBody TodayMessageRequestDto todayMessageRequestDto) {
        return ResponseEntity.ok(todayMessageService.makeInfo(authentication.getName(), todayMessageRequestDto));
    }
}
