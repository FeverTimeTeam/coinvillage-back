package com.fevertime.coinvillage.service;

import com.fevertime.coinvillage.domain.account.Account;
import com.fevertime.coinvillage.domain.account.Savings;
import com.fevertime.coinvillage.domain.account.SavingsSetting;
import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.repository.AccountRepository;
import com.fevertime.coinvillage.repository.MemberRepository;
import com.fevertime.coinvillage.repository.SavingsRepository;
import com.fevertime.coinvillage.repository.SavingsSettingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final SavingsRepository savingsRepository;
    private final SavingsSettingRepository savingsSettingRepository;

    @Transactional
    @Scheduled(cron = "0/3 * * * * ?")
    public void run() {
        List<SavingsSetting> savingsSettingList = savingsSettingRepository.findAll();
        for (SavingsSetting savingsSetting : savingsSettingList) {
            // 모든 국민의 적금 세팅 조회
            SavingsSetting savingsSettings = savingsSettingRepository.findById(savingsSetting.getSettingsId()).orElseThrow(() -> new IllegalArgumentException("없음"));
            Member member = memberRepository.findByEmail(savingsSettings.getMember().getEmail());
            List<Account> accounts = accountRepository.findAllByMember_Email(savingsSettings.getMember().getEmail());
            Account account = accounts.get(accounts.size() - 1);

            // 만기가 0이면 만기가 0이고 적금금액 0으로 변경, 아니라면 만기가 -1씩 적용
            savingsSettings.updateMaturity();
            savingsSettingRepository.save(savingsSettings);

            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formatedNow = now.format(formatter);
            String currentMonth = now.format(DateTimeFormatter.ofPattern("MM"));

            // 적금테이블에 적금 로그 작성
            Savings savings = Savings.builder()
                    .stateName(StateName.DEPOSIT)
                    .savingsTotal(account.getMember().getSavingsTotal() + savingsSettings.getBill())
                    .total(savingsSettings.getBill())
                    .content(currentMonth + "월 " + koreanDate(returnWeek(formatedNow)) + "주 적금")
                    .account(account)
                    .build();
            if (savingsSettings.getBill() == 0) {
                continue;
            } else {
                savingsRepository.save(savings);
            }


            // 회원의 적금총액, 입출금총액, 전체자산 변경
            member.changeSavingsTotal(savings.getSavingsTotal());
            member.changeAccountTotal(member.getAccountTotal() - savingsSettings.getBill());
            member.changeProperty(savings.getSavingsTotal() + member.getAccountTotal() + member.getStockTotal());
            memberRepository.save(member);

            // 입출금 테이블에 입출금 로그 작성
            Account account1 = Account.builder()
                    .accountTotal(member.getAccountTotal())
                    .content(currentMonth + "월 " + koreanDate(returnWeek(formatedNow)) + "주 적금")
                    .count(0L)
                    .total(savingsSettings.getBill())
                    .stateName(StateName.WITHDRAWL)
                    .member(member)
                    .build();

            if (savingsSettings.getBill() == 0) {
                continue;
            } else {
                accountRepository.save(account);
                accountRepository.save(account1);
            }
        }
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
