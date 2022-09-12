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
        accountRequestDto.setAccountTotal(member.getAccountTotal() - accountRequestDto.getTotal());
        member.changeAccountTotal(member.getAccountTotal() - accountRequestDto.getTotal());
        member.changeProperty(member.getAccountTotal() + member.getSavingsTotal() + member.getStockTotal());

        Account account = accountRequestDto.toEntity();

        accountRepository.save(account);
        memberRepository.save(member);

        return new AccountResponseDto(account);
    }

    // 입출금에서 주식통장으로 입금
    public AccountResponseDto stockDeposit(String email, AccountRequestDto accountRequestDto) {
        Member member = memberRepository.findByEmail(email);
        
        Account account = Account.builder()
                .total(accountRequestDto.getTotal())
                .stateName(StateName.WITHDRAWL)
                .accountTotal(member.getAccountTotal() - accountRequestDto.getTotal())
                .content("주식통장으로 입금")
                .member(member)
                .build();

        member.changeAccountTotal(member.getAccountTotal() - accountRequestDto.getTotal());
        member.changeStockTotal(member.getStockTotal() + accountRequestDto.getTotal());

        accountRepository.save(account);
        memberRepository.save(member);

        return new AccountResponseDto(account);
    }
}
