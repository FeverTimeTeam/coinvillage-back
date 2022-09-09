package com.fevertime.coinvillage.service;

import com.fevertime.coinvillage.domain.account.Account;
import com.fevertime.coinvillage.dto.account.AccountRequestDto;
import com.fevertime.coinvillage.dto.account.AccountResponseDto;
import com.fevertime.coinvillage.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    
    // 통장 내역 보기
    public List<AccountResponseDto> showAccounts(String email) {
        List<Account> accounts = accountRepository.findAllByMember_Email(email);
        return accounts.stream()
                .map(AccountResponseDto::new)
                .collect(Collectors.toList());
    }

    // 통장 구매 API
}
