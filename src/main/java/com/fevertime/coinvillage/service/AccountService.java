package com.fevertime.coinvillage.service;

import com.fevertime.coinvillage.domain.account.Account;
import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.dto.account.AccountRequestDto;
import com.fevertime.coinvillage.dto.account.AccountResponseDto;
import com.fevertime.coinvillage.repository.AccountRepository;
import com.fevertime.coinvillage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;
    
    // 통장 내역 보기
    public List<AccountResponseDto> showAccounts(String email) {
        List<Account> accounts = accountRepository.findAllByMember_Email(email);
        return accounts.stream()
                .map(AccountResponseDto::new)
                .collect(Collectors.toList());
    }

    // 통장 소비 API
    public AccountResponseDto consumeAccount(String email, AccountRequestDto accountRequestDto) {
        Member member = memberRepository.findByEmail(email);

        accountRequestDto.setMember(member);
        accountRequestDto.setStateName(StateName.WITHDRAWL);
        accountRequestDto.setAccountTotal(member.getAccountList().get(member.getAccountList().size() - 1).getAccountTotal() - accountRequestDto.getTotal());

        Account account = accountRequestDto.toEntity();

        accountRepository.save(account);
        memberRepository.save(member);

        return new AccountResponseDto(account);
    }
}
