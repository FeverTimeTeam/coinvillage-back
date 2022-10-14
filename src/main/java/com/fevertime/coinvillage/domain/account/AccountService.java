package com.fevertime.coinvillage.domain.account;

import com.fevertime.coinvillage.domain.account.entity.AccountHistory;
import com.fevertime.coinvillage.domain.account.repository.AccountHistoryRepository;
import com.fevertime.coinvillage.domain.member.entity.Member;
import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.domain.account.dto.AccountHistoryRequestDto;
import com.fevertime.coinvillage.domain.account.dto.AccountHistoryResponseDto;
import com.fevertime.coinvillage.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountHistoryRepository accountHistoryRepository;
    private final MemberRepository memberRepository;
    
    // 통장 내역 보기
    @Transactional(readOnly = true)
    public List<AccountHistoryResponseDto> showAccounts(String email) {
        List<AccountHistory> accountHistories = accountHistoryRepository.findAllByAccount_Member_Email(email);
        return accountHistories.stream()
                .map(AccountHistoryResponseDto::new)
                .collect(Collectors.toList());
    }

    // 통장 소비 API
    public AccountHistoryResponseDto consumeAccount(String email, AccountHistoryRequestDto accountHistoryRequestDto) {
        Member member = memberRepository.findByEmail(email);

        accountHistoryRequestDto.setAccount(member.getAccount());
        accountHistoryRequestDto.setStateName(StateName.WITHDRAWL);
        accountHistoryRequestDto.setAccountTotal(member.getAccount().getAccountTotal()
                - accountHistoryRequestDto.getTotal());
        member.getAccount().changeAccountTotal(member.getAccount().getAccountTotal()
                - accountHistoryRequestDto.getTotal());
        member.changeProperty(member.getAccount().getAccountTotal()
                + member.getSavings().getSavingsTotal()
                + member.getStock().getStockTotal());

        AccountHistory accountHistory = accountHistoryRequestDto.toEntity();

        accountHistoryRepository.save(accountHistory);
        memberRepository.save(member);

        return new AccountHistoryResponseDto(accountHistory);
    }

    // 입출금에서 주식통장으로 입금
    public AccountHistoryResponseDto stockDeposit(String email, AccountHistoryRequestDto accountHistoryRequestDto) {
        Member member = memberRepository.findByEmail(email);
        
        AccountHistory accountHistory = AccountHistory.builder()
                .total(accountHistoryRequestDto.getTotal())
                .stateName(StateName.WITHDRAWL)
                .content("주식통장으로 입금")
                .account(member.getAccount())
                .build();

        member.getAccount().changeAccountTotal(member.getAccount().getAccountTotal()
                - accountHistoryRequestDto.getTotal());
        member.getStock().changeStockTotal(member.getStock().getStockTotal()
                + accountHistoryRequestDto.getTotal());

        accountHistoryRepository.save(accountHistory);
        memberRepository.save(member);

        return new AccountHistoryResponseDto(accountHistory);
    }
}
