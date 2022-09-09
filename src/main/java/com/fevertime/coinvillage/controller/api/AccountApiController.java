package com.fevertime.coinvillage.controller.api;

import com.fevertime.coinvillage.dto.account.AccountRequestDto;
import com.fevertime.coinvillage.dto.account.AccountResponseDto;
import com.fevertime.coinvillage.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "통장관리")
@RequestMapping("accounts")
public class AccountApiController {
    private final AccountService accountService;

    @GetMapping
    @ApiOperation(value = "입출금내역")
    public ResponseEntity<List<AccountResponseDto>> showAccounts(Authentication authentication) {
        return ResponseEntity.ok(accountService.showAccounts(authentication.getName()));
    }

    @PostMapping
    @ApiOperation("소비하기")
    public ResponseEntity<AccountResponseDto> consumeAccount(Authentication authentication, @RequestBody AccountRequestDto accountRequestDto) {
        return ResponseEntity.ok(accountService.consumeAccount(authentication.getName(), accountRequestDto));
    }
}
