package com.fevertime.coinvillage;

import com.fevertime.coinvillage.domain.account.Account;
import com.fevertime.coinvillage.domain.account.SavingsSetting;
import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.repository.AccountRepository;
import com.fevertime.coinvillage.repository.MemberRepository;
import com.fevertime.coinvillage.repository.SavingsSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.fevertime.coinvillage.domain.model.Term.MONTHLY;
import static com.fevertime.coinvillage.domain.model.Term.WEEKLY;

@SpringBootTest
public class Test {
    @Autowired
    private SavingsSettingRepository savingsSettingRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AccountRepository accountRepository;

    @org.junit.jupiter.api.Test
    public void test() {
        SavingsSetting savingsSettings = savingsSettingRepository.findById(5L).orElseThrow(() -> new IllegalArgumentException("시발"));
        System.out.println(savingsSettings.getMember().getEmail());
        Member member = memberRepository.findByEmail(savingsSettings.getMember().getEmail());
        System.out.println(member.getEmail());
        List<Account> accounts = accountRepository.findAllByMember_Email(savingsSettings.getMember().getEmail());
        System.out.println(accounts.get(accounts.size() - 1).getContent());
    }

    @org.junit.jupiter.api.Test
    void test2() {
        SavingsSetting savingsSettings = savingsSettingRepository.findById(5L).orElseThrow(() -> new IllegalArgumentException("없음"));
        if (savingsSettings.getTerm() == MONTHLY) {
            String result = "0 " + "0 " + "9 " + savingsSettings.getDay() + " * " + "?";
            System.out.println(result);
        }

    }

}
