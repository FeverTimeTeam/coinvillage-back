package com.fevertime.coinvillage.domain.savings;

import com.fevertime.coinvillage.domain.account.repository.AccountHistoryRepository;
import com.fevertime.coinvillage.domain.account.entity.AccountHistory;
import com.fevertime.coinvillage.domain.country.repository.CountryRepository;
import com.fevertime.coinvillage.domain.member.repository.MemberRepository;
import com.fevertime.coinvillage.domain.member.entity.Authority;
import com.fevertime.coinvillage.domain.model.Role;
import com.fevertime.coinvillage.domain.savings.entity.SavingsHistory;
import com.fevertime.coinvillage.domain.savings.entity.SavingsSetting;
import com.fevertime.coinvillage.domain.country.entity.Country;
import com.fevertime.coinvillage.domain.member.entity.Member;
import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.domain.savings.dto.InterestResponseDto;
import com.fevertime.coinvillage.domain.savings.dto.SavingsHistoryResponseDto;
import com.fevertime.coinvillage.domain.savings.dto.SavingsSettingRequestDto;
import com.fevertime.coinvillage.domain.savings.dto.SavingsSettingResponseDto;
import com.fevertime.coinvillage.domain.savings.repository.SavingsHistoryRepository;
import com.fevertime.coinvillage.domain.savings.repository.SavingsSettingRepository;
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
    private final MemberRepository memberRepository;
    private final SavingsSettingRepository savingsSettingRepository;
    private final SavingsHistoryRepository savingsHistoryRepository;
    private final CountryRepository countryRepository;
    private final AccountHistoryRepository accountHistoryRepository;

    // 적금 내역 보기
    @Transactional(readOnly = true)
    public List<SavingsHistoryResponseDto> showSavings(String email) {
        List<SavingsHistory> savingsHistories = savingsHistoryRepository.findBySavings_Member_Email(email);
        return savingsHistories.stream()
                .map(SavingsHistoryResponseDto::new)
                .collect(Collectors.toList());
    }

    // 적금 날짜, 적금만기 이자, 세금 세팅하기(선생님)
    @Transactional
    public List<SavingsSettingResponseDto> stackSavings(String email, SavingsSettingRequestDto savingsSettingRequestDto) {
        Country country = countryRepository.findByCountryName(memberRepository.findByEmail(email).getCountry().getCountryName());
        country.changeTax(savingsSettingRequestDto.getTax());
        countryRepository.save(country);

        Authority authority = Authority.builder()
                .authorityName(Role.ROLE_NATION)
                .build();

        List<SavingsSetting> savingsSetting = savingsSettingRepository.findAllBySavings_Member_Country_CountryNameAndSavings_Member_AuthoritiesIn(country.getCountryName(), Collections.singleton(authority));
        savingsSetting.forEach(a -> a.updateDay(savingsSettingRequestDto.getDay())); // 날짜
        savingsSetting.forEach(b -> b.updateInterest(savingsSettingRequestDto.getInterest())); // 이자
        savingsSettingRepository.saveAll(savingsSetting);

        return savingsSetting.stream()
                .map(SavingsSettingResponseDto::new)
                .collect(Collectors.toList());
    }

    // 현재 설정한 세팅값(선생님)
    @Transactional(readOnly = true)
    public SavingsSettingResponseDto showSetting(String email) {
        Country country = countryRepository.findByCountryName(memberRepository.findByEmail(email).getCountry().getCountryName());
        Authority authority = Authority.builder()
                .authorityName(Role.ROLE_NATION)
                .build();
        SavingsSetting savingsSetting = savingsSettingRepository
                .findAllBySavings_Member_Country_CountryNameAndSavings_Member_AuthoritiesIn(country.getCountryName(), Collections.singleton(authority)).get(0);

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
        SavingsSetting savingsSetting = savingsSettingRepository.findBySavings_Member_Email(email);
        savingsSetting.updateBill(savingsSettingRequestDto.getBill());
        savingsSettingRepository.save(savingsSetting);

        return new SavingsSettingResponseDto(savingsSetting);
    }

    // 적금 만기 수령받기(학생)
    @Transactional
    public void receiveSavings(String email) {
        Member member = memberRepository.findByEmail(email);
        Long savingsExpiration = member.getSavings().getSavingsTotal() * ((member.getSavings().getSavingsSetting().getInterest() / 100) + 1);
        member.getAccount().changeAccountTotal(member.getAccount().getAccountTotal() + savingsExpiration);
        member.getSavings().changeSavingsTotal(0L);
        member.changeProperty(member.getAccount().getAccountTotal() + savingsExpiration + member.getStock().getStockTotal());
        memberRepository.save(member);

        SavingsHistory savingsHistory = SavingsHistory.builder()
                .stateName(StateName.WITHDRAWL)
                .total(savingsExpiration)
                .content("적금 만기")
                .savings(member.getSavings())
                .build();
        savingsHistoryRepository.save(savingsHistory);

        AccountHistory accountHistory = AccountHistory.builder()
                .content("적금 만기")
                .count(0L)
                .total(savingsExpiration)
                .stateName(StateName.DEPOSIT)
                .account(member.getAccount())
                .build();
        accountHistoryRepository.save(accountHistory);
    }

    // 이자 보여주기
    @Transactional(readOnly = true)
    public InterestResponseDto showInterest(String email) {
        SavingsSetting savingsSetting = savingsSettingRepository.findBySavings_Member_Email(email);
        return new InterestResponseDto(savingsSetting);
    }
}
