package com.fevertime.coinvillage.domain.account;

import com.fevertime.coinvillage.domain.account.dto.AccountHistoryRequestDto;
import com.fevertime.coinvillage.domain.account.dto.AccountHistoryResponseDto;
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
    public ResponseEntity<List<AccountHistoryResponseDto>> showAccounts(Authentication authentication) {
        return ResponseEntity.ok(accountService.showAccounts(authentication.getName()));
    }

    @PostMapping
    @ApiOperation(value = "소비하기")
    public ResponseEntity<AccountHistoryResponseDto> consumeAccount(Authentication authentication, @RequestBody AccountHistoryRequestDto accountHistoryRequestDto) {
        return ResponseEntity.ok(accountService.consumeAccount(authentication.getName(), accountHistoryRequestDto));
    }

    @PostMapping("stock")
    @ApiOperation(value = "주식통장으로 입금")
    public ResponseEntity<AccountHistoryResponseDto> stockDeposit(Authentication authentication, @RequestBody AccountHistoryRequestDto accountHistoryRequestDto) {
        return ResponseEntity.ok(accountService.stockDeposit(authentication.getName(), accountHistoryRequestDto));
    }
}
