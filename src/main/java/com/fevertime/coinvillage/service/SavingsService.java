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

    // 적금, 세금 세팅(선생님)
    @Transactional
    public List<SavingsSettingResponseDto> stackSavings(String email, SavingsSettingRequestDto savingsSettingRequestDto) {
        Country country = countryRepository.findByCountryName(memberRepository.findByEmail(email).getCountry().getCountryName());
        country.changeTax(savingsSettingRequestDto.getTax());
        countryRepository.save(country);

        List<SavingsSetting> savingsSetting = savingsSettingRepository.findAllByMember_Country_CountryName(country.getCountryName());
        savingsSetting.forEach(a -> a.updateDay(savingsSettingRequestDto.getDay()));
        savingsSettingRepository.saveAll(savingsSetting);

        return savingsSetting.stream()
                .map(SavingsSettingResponseDto::new)
                .collect(Collectors.toList());
    }

    // 적금 세팅 수정하기(학생)
    @Transactional
    public SavingsSettingResponseDto modSavings(String email, SavingsSettingRequestDto savingsSettingRequestDto) {
        SavingsSetting savingsSetting = savingsSettingRepository.findByMember_Email(email);
        savingsSetting.updateBill(savingsSettingRequestDto.getBill());
        savingsSettingRepository.save(savingsSetting);

        return new SavingsSettingResponseDto(savingsSetting);
    }
}
