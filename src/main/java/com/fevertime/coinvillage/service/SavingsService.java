package com.fevertime.coinvillage.service;

import com.fevertime.coinvillage.domain.account.Account;
import com.fevertime.coinvillage.domain.account.Savings;
import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.dto.savings.SavingsRequestDto;
import com.fevertime.coinvillage.dto.savings.SavingsResponseDto;
import com.fevertime.coinvillage.repository.AccountRepository;
import com.fevertime.coinvillage.repository.MemberRepository;
import com.fevertime.coinvillage.repository.SavingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SavingsService {
    private final SavingsRepository savingsRepository;
    private final AccountRepository accountRepository;
    private final MemberRepository memberRepository;

    // 적금 내역 보기
    public List<SavingsResponseDto> showSavings(String email) {
        List<Savings> savings = savingsRepository.findByAccount_Member_Email(email);
        return savings.stream()
                .map(SavingsResponseDto::new)
                .collect(Collectors.toList());
    }

    // 적금하기
    public SavingsResponseDto stackSavings(String email) {
        List<Account> accounts = accountRepository.findAllByMember_Email(email);
        Account account = accounts.get(accounts.size() - 1);

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatedNow = now.format(formatter);
        String currentMonth = now.format(DateTimeFormatter.ofPattern("MM"));

        Savings savings = Savings.builder()
                .savingsTotal(account.getSavingsList().get(account.getSavingsList().size() - 1).getSavingsTotal() + 30)
                .total(30L)
                .content(currentMonth + "월 " + koreanDate(returnWeek(formatedNow)) + "주 적금")
                .account(account)
                .build();

        savingsRepository.save(savings);

        account.stack(30L);

        accountRepository.save(account);

        return new SavingsResponseDto(savings);
    }

    public int returnWeek(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            log.info("에러났어용");
        }
        // 날짜 입력하는곳 .
        date = new Date(date.getTime() + (1000 * 60 * 60 * 24 - 1));
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    public String koreanDate(int strDate) {
        Map map = new HashMap<Integer, Object>();
        map.put(1, "첫째");
        map.put(2, "둘째");
        map.put(3, "셋째");
        map.put(4, "넷째");
        map.put(5, "다섯째");
        return (String) map.get(strDate);
    }
}
