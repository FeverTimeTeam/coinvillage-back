package com.fevertime.coinvillage.domain.savings;

import com.fevertime.coinvillage.domain.account.repository.AccountHistoryRepository;
import com.fevertime.coinvillage.domain.account.entity.AccountHistory;
import com.fevertime.coinvillage.domain.member.repository.MemberRepository;
import com.fevertime.coinvillage.domain.savings.entity.SavingsHistory;
import com.fevertime.coinvillage.domain.savings.entity.SavingsSetting;
import com.fevertime.coinvillage.domain.member.entity.Member;
import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.domain.savings.repository.SavingsHistoryRepository;
import com.fevertime.coinvillage.domain.savings.repository.SavingsSettingRepository;
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

    @Transactional
    @Scheduled(cron = "0 0 0 1/1 * ?")
    public void run() {
        List<SavingsSetting> savingsSettingList = savingsSettingRepository.findAll();

        LocalDate now = LocalDate.now();
        // 현재 월
        String currentMonth = now.format(DateTimeFormatter.ofPattern("MM"));
        // 현재 일
        String currentDay = now.format(DateTimeFormatter.ofPattern("dd"));

        for (SavingsSetting savingsSetting : savingsSettingList) {

            // 모든 국민의 적금 세팅 조회
            SavingsSetting savingsSettings = savingsSettingRepository.findById(savingsSetting.getSettingsId()).orElseThrow(() -> new IllegalArgumentException("없음"));

            // 설정한 일이 현재 일과 같다면 적금, 입출금 통장에 기록 남기기
            if (savingsSettings.getDay().equals(currentDay)) {
                Member member = memberRepository.findByEmail(savingsSettings.getSavings().getMember().getEmail());

                // 만기가 0이면 만기가 0이고 적금금액 0으로 변경, 아니라면 만기가 -1씩 적용
                savingsSettings.updateMaturity();
                savingsSettingRepository.save(savingsSettings);

                if (savingsSettings.getBill() != 0) {
                    // 적금테이블에 적금 로그 작성
                    SavingsHistory savingsHistory = SavingsHistory.builder()
                            .stateName(StateName.DEPOSIT)
                            .total(savingsSettings.getBill())
                            .content(currentMonth + "월 적금")
                            .savings(member.getSavings())
                            .build();
                    savingsHistoryRepository.save(savingsHistory);

                    // 회원의 적금총액, 입출금총액, 전체자산 변경
                    member.getSavings().changeSavingsTotal(member.getSavings().getSavingsTotal());
                    member.getAccount().changeAccountTotal(member.getAccount().getAccountTotal() - savingsSettings.getBill());
                    member.changeProperty(savingsHistory.getSavings().getSavingsTotal()
                            + member.getAccount().getAccountTotal()
                            + member.getStock().getStockTotal());
                    memberRepository.save(member);

                    // 입출금 테이블에 입출금 로그 작성
                    AccountHistory accountHistory = AccountHistory.builder()
                            .content(currentMonth + "월 적금")
                            .count(0L)
                            .total(savingsSettings.getBill())
                            .stateName(StateName.WITHDRAWL)
                            .account(member.getAccount())
                            .build();
                    accountHistoryRepository.save(accountHistory);

                    log.info(currentDay + "일 적금 완료!");
                }
            }
        }
    }
}
