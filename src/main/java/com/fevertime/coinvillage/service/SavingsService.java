package com.fevertime.coinvillage.service;

import com.fevertime.coinvillage.domain.account.Account;
import com.fevertime.coinvillage.domain.account.Savings;
import com.fevertime.coinvillage.domain.account.SavingsSetting;
import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.domain.model.Term;
import com.fevertime.coinvillage.dto.savings.SavingsRequestDto;
import com.fevertime.coinvillage.dto.savings.SavingsResponseDto;
import com.fevertime.coinvillage.dto.savings.SavingsSettingRequestDto;
import com.fevertime.coinvillage.dto.savings.SavingsSettingResponseDto;
import com.fevertime.coinvillage.repository.AccountRepository;
import com.fevertime.coinvillage.repository.MemberRepository;
import com.fevertime.coinvillage.repository.SavingsRepository;
import com.fevertime.coinvillage.repository.SavingsSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SavingsService {
    private final SavingsRepository savingsRepository;
    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;
    private final SavingsSettingRepository savingsSettingRepository;

    // 적금 내역 보기
    @Transactional
    public List<SavingsResponseDto> showSavings(String email) {
        List<Savings> savings = savingsRepository.findByAccount_Member_Email(email);
        return savings.stream()
                .map(SavingsResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public SavingsSettingResponseDto stackSavings(String email, SavingsSettingRequestDto savingsSettingRequestDto) {
        Member member = memberRepository.findByEmail(email);

        SavingsSetting savingsSetting = SavingsSetting.builder()
                .term(Term.MONTHLY)
                .bill(savingsSettingRequestDto.getBill())
                .member(member)
                .build();

        savingsSettingRepository.save(savingsSetting);

        return new SavingsSettingResponseDto(savingsSetting);
    }

    @Transactional
    public SavingsSettingResponseDto modSavings(String email, SavingsSettingRequestDto savingsSettingRequestDto) {
        SavingsSetting savingsSetting = savingsSettingRepository.findByMember_Email(email);

        savingsSetting.updateBill(savingsSettingRequestDto.getBill());

        savingsSettingRepository.save(savingsSetting);

        return new SavingsSettingResponseDto(savingsSetting);
    }
}
