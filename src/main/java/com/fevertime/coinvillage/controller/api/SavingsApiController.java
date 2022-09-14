package com.fevertime.coinvillage.controller.api;

import com.fevertime.coinvillage.dto.savings.SavingsResponseDto;
import com.fevertime.coinvillage.dto.savings.SavingsSettingRequestDto;
import com.fevertime.coinvillage.dto.savings.SavingsSettingResponseDto;
import com.fevertime.coinvillage.service.SavingsService;
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
@RequestMapping("savings")
@Api(tags = "적금")
public class SavingsApiController {
    private final SavingsService savingsService;

    @GetMapping
    @ApiOperation(value = "적금 목록")
    public ResponseEntity<List<SavingsResponseDto>> showSavings(Authentication authentication) {
        return ResponseEntity.ok(savingsService.showSavings(authentication.getName()));
    }

    @PutMapping("setting")
    @PreAuthorize("hasRole('ROLE_RULER')")
    @ApiOperation(value = "적금 날짜, 적금만기 이자, 세금 세팅하기(선생님)")
    public ResponseEntity<List<SavingsSettingResponseDto>> stackSavingsSetting(Authentication authentication, @RequestBody SavingsSettingRequestDto savingsSettingRequestDto) {
        return ResponseEntity.ok(savingsService.stackSavings(authentication.getName(), savingsSettingRequestDto));
    }

    @PutMapping("change")
    @ApiOperation(value = "적금 금액 세팅하기(학생)")
    public ResponseEntity<SavingsSettingResponseDto> modSavingsSetting(Authentication authentication, @RequestBody SavingsSettingRequestDto savingsSettingRequestDto) {
        return ResponseEntity.ok(savingsService.modSavings(authentication.getName(), savingsSettingRequestDto));
    }

    @PostMapping
    @ApiOperation(value = "적금 만기 수령(학생)")
    public void receiveSavings(Authentication authentication) {
        savingsService.receiveSavings(authentication.getName());
    }
}
