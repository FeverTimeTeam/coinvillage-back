package com.fevertime.coinvillage.controller.api;

import com.fevertime.coinvillage.dto.savings.SavingsResponseDto;
import com.fevertime.coinvillage.service.SavingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    @ApiOperation(value = "적금하기")
    public ResponseEntity<SavingsResponseDto> stackSavings(Authentication authentication) {
        return ResponseEntity.ok(savingsService.stackSavings(authentication.getName()));
    }
}
