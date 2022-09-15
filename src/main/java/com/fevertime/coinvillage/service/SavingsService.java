package com.fevertime.coinvillage.service;

import com.fevertime.coinvillage.domain.account.Account;
import com.fevertime.coinvillage.domain.account.Savings;
import com.fevertime.coinvillage.domain.account.SavingsSetting;
import com.fevertime.coinvillage.domain.country.Country;
import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.dto.savings.InterestResponseDto;
import com.fevertime.coinvillage.dto.savings.SavingsResponseDto;
import com.fevertime.coinvillage.dto.savings.SavingsSettingRequestDto;
import com.fevertime.coinvillage.dto.savings.SavingsSettingResponseDto;
import com.fevertime.coinvillage.repository.*;
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
    private final AccountRepository accountRepository;

    // 적금 내역 보기
    @Transactional
    public List<SavingsResponseDto> showSavings(String email) {
        List<Savings> savings = savingsRepository.findByAccount_Member_Email(email);
        return savings.stream()
                .map(SavingsResponseDto::new)
                .collect(Collectors.toList());
    }

    // 적금 날짜, 적금만기 이자, 세금 세팅하기(선생님)
    @Transactional
    public List<SavingsSettingResponseDto> stackSavings(String email, SavingsSettingRequestDto savingsSettingRequestDto) {
        Country country = countryRepository.findByCountryName(memberRepository.findByEmail(email).getCountry().getCountryName());
        country.changeTax(savingsSettingRequestDto.getTax());
        countryRepository.save(country);

        List<SavingsSetting> savingsSetting = savingsSettingRepository.findAllByMember_Country_CountryName(country.getCountryName());
        savingsSetting.forEach(a -> a.updateDay(savingsSettingRequestDto.getDay()));
        savingsSetting.forEach(b -> b.updateInterest(savingsSettingRequestDto.getInterest()));
        savingsSettingRepository.saveAll(savingsSetting);

        return savingsSetting.stream()
                .map(SavingsSettingResponseDto::new)
                .collect(Collectors.toList());
    }

    // 현재 설정한 세팅값(선생님)
    @Transactional
    public SavingsSettingResponseDto showSetting(String email) {
        Country country = countryRepository.findByCountryName(memberRepository.findByEmail(email).getCountry().getCountryName());
        SavingsSetting savingsSetting = savingsSettingRepository.findAllByMember_Country_CountryName(country.getCountryName()).get(0);

        return SavingsSettingResponseDto.builder()
                .tax(country.getTax())
                .day(savingsSetting.getDay())
                .interest(savingsSetting.getInterest())
                .maturity(savingsSetting.getMaturity())
                .build();
    }

    // 적금 세팅 수정하기(학생)
    @Transactional
    public SavingsSettingResponseDto modSavings(String email, SavingsSettingRequestDto savingsSettingRequestDto) {
        SavingsSetting savingsSetting = savingsSettingRepository.findByMember_Email(email);
        savingsSetting.updateBill(savingsSettingRequestDto.getBill());
        savingsSettingRepository.save(savingsSetting);

        return new SavingsSettingResponseDto(savingsSetting);
    }

    // 적금 만기 수령받기(학생)
    @Transactional
    public void receiveSavings(String email) {
        Member member = memberRepository.findByEmail(email);
        Long savingsExpiration = member.getSavingsTotal() * ((member.getSavingsSetting().getInterest() / 100) + 1);
        member.changeAccountTotal(member.getAccountTotal() + savingsExpiration);
        member.changeSavingsTotal(0L);
        member.changeProperty(member.getAccountTotal() + savingsExpiration + member.getStockTotal());
        memberRepository.save(member);

        List<Account> accounts = accountRepository.findAllByMember_Email(email);
        Account account = accounts.get(accounts.size() - 1);

        Savings savings = Savings.builder()
                .stateName(StateName.WITHDRAWL)
                .savingsTotal(0L)
                .total(savingsExpiration)
                .content("적금 만기")
                .account(account)
                .build();
        savingsRepository.save(savings);

        Account account1 = Account.builder()
                .accountTotal(member.getAccountTotal())
                .content("적금 만기")
                .count(0L)
                .total(savingsExpiration)
                .stateName(StateName.DEPOSIT)
                .member(member)
                .build();
        accountRepository.save(account);
        accountRepository.save(account1);
    }

    // 이자 보여주기
    @Transactional
    public InterestResponseDto showInterest(String email) {
        SavingsSetting savingsSetting = savingsSettingRepository.findByMember_Email(email);
        return new InterestResponseDto(savingsSetting);
    }
}
