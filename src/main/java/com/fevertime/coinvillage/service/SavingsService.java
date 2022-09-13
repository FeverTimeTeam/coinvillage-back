package com.fevertime.coinvillage.service;

import com.fevertime.coinvillage.domain.account.Savings;
import com.fevertime.coinvillage.domain.account.SavingsSetting;
import com.fevertime.coinvillage.domain.country.Country;
import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.domain.model.Term;
import com.fevertime.coinvillage.dto.savings.SavingsResponseDto;
import com.fevertime.coinvillage.dto.savings.SavingsSettingRequestDto;
import com.fevertime.coinvillage.dto.savings.SavingsSettingResponseDto;
import com.fevertime.coinvillage.repository.CountryRepository;
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
    private final MemberRepository memberRepository;
    private final SavingsSettingRepository savingsSettingRepository;
    private final CountryRepository countryRepository;

    // 적금 내역 보기
    @Transactional
    public List<SavingsResponseDto> showSavings(String email) {
        List<Savings> savings = savingsRepository.findByAccount_Member_Email(email);
        return savings.stream()
                .map(SavingsResponseDto::new)
                .collect(Collectors.toList());
    }

    // 적금, 세금 세팅
    @Transactional
    public SavingsSettingResponseDto stackSavings(String email, SavingsSettingRequestDto savingsSettingRequestDto) {
        Member member = memberRepository.findByEmail(email);

        Country country = countryRepository.findByCountryName(memberRepository.findByEmail(email).getCountry().getCountryName());
        country.changeTax(savingsSettingRequestDto.getTax());

        countryRepository.save(country);

        SavingsSetting savingsSetting = SavingsSetting.builder()
                .term(Term.MONTHLY)
                .bill(savingsSettingRequestDto.getBill())
                .member(member)
                .build();

        savingsSettingRepository.save(savingsSetting);

        return new SavingsSettingResponseDto(savingsSetting);
    }

    // 적금 세팅 수정하기
    @Transactional
    public SavingsSettingResponseDto modSavings(String email, SavingsSettingRequestDto savingsSettingRequestDto) {
        log.info("시작");
        SavingsSetting savingsSetting = savingsSettingRepository.findByMember_Email(email);
        log.info(savingsSetting.getDay());
        savingsSetting.updateBill(savingsSettingRequestDto.getBill());
        savingsSettingRepository.save(savingsSetting);

        Country country = countryRepository.findByCountryName(memberRepository.findByEmail(email).getCountry().getCountryName());
        log.info(country.getCountryName());
        country.changeTax(savingsSettingRequestDto.getTax());
        countryRepository.save(country);

        return new SavingsSettingResponseDto(savingsSetting);
    }
}
