package com.fevertime.coinvillage.service;

import com.fevertime.coinvillage.domain.account.Account;
import com.fevertime.coinvillage.domain.account.AccountHistory;
import com.fevertime.coinvillage.domain.savings.Savings;
import com.fevertime.coinvillage.domain.savings.SavingsHistory;
import com.fevertime.coinvillage.domain.savings.SavingsSetting;
import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.repository.*;
import com.fevertime.coinvillage.util.SpringDynamicCornTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {
    private final MemberRepository memberRepository;
    private final AccountHistoryRepository accountHistoryRepository;
    private final SavingsHistoryRepository savingsHistoryRepository;
    private final SavingsSettingRepository savingsSettingRepository;
    private final SpringDynamicCornTask springDynamicCornTask;

    @Transactional
    @Scheduled(cron = "0 0 0 1 * *")
    public void run() {
        List<SavingsSetting> savingsSettingList = savingsSettingRepository.findAll();
        for (SavingsSetting savingsSetting : savingsSettingList) {

            // 모든 국민의 적금 세팅 조회
            SavingsSetting savingsSettings = savingsSettingRepository.findById(savingsSetting.getSettingsId()).orElseThrow(() -> new IllegalArgumentException("없음"));
            String customCron = "0 0 0 " + savingsSettings.getDay() + " * *";
            springDynamicCornTask.setCron(customCron);
            Member member = memberRepository.findByEmail(savingsSettings.getSavings().getMember().getEmail());

            // 만기가 0이면 만기가 0이고 적금금액 0으로 변경, 아니라면 만기가 -1씩 적용
            savingsSettings.updateMaturity();
            savingsSettingRepository.save(savingsSettings);

            LocalDate now = LocalDate.now();
            String currentMonth = now.format(DateTimeFormatter.ofPattern("MM"));

            // 적금테이블에 적금 로그 작성
            SavingsHistory savingsHistory = SavingsHistory.builder()
                    .stateName(StateName.DEPOSIT)
                    .total(savingsSettings.getBill())
                    .content(currentMonth + "월 적금")
                    .savings(member.getSavings())
                    .build();
            if (savingsSettings.getBill() == 0) {
                continue;
            } else {
                savingsHistoryRepository.save(savingsHistory);
            }

            // 회원의 적금총액, 입출금총액, 전체자산 변경
            member.getSavings().changeSavingsTotal(member.getSavings().getSavingsTotal());
            member.getAccount().changeAccountTotal(member.getAccount().getAccountTotal() - savingsSettings.getBill());
            member.changeProperty(savingsHistory.getSavings().getSavingsTotal() + member.getAccount().getAccountTotal() + member.getStock().getStockTotal());
            memberRepository.save(member);

            // 입출금 테이블에 입출금 로그 작성
            AccountHistory accountHistory = AccountHistory.builder()
                    .content(currentMonth + "월 적금")
                    .count(0L)
                    .total(savingsSettings.getBill())
                    .stateName(StateName.WITHDRAWL)
                    .account(member.getAccount())
                    .build();

            if (savingsSettings.getBill() == 0) {
                continue;
            } else {
                accountHistoryRepository.save(accountHistory);
            }
        }
    }
}
