package com.fevertime.coinvillage.service;

import com.fevertime.coinvillage.domain.account.Account;
import com.fevertime.coinvillage.domain.account.AccountHistory;
import com.fevertime.coinvillage.domain.member.Authority;
import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.domain.model.Role;
import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.dto.manage.ManageResponseDto;
import com.fevertime.coinvillage.dto.manage.ManageUpdateRequestDto;
import com.fevertime.coinvillage.repository.AccountHistoryRepository;
import com.fevertime.coinvillage.repository.AccountRepository;
import com.fevertime.coinvillage.repository.JobRepository;
import com.fevertime.coinvillage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManageService {
    private final MemberRepository memberRepository;
    private final JobRepository jobRepository;
    private final AccountRepository accountRepository;
    private final AccountHistoryRepository accountHistoryRepository;

    // 국민관리 회원 전체보기
    @Transactional(readOnly = true)
    public List<ManageResponseDto> showMembers(String email) {
        Authority authority = Authority.builder()
                .authorityName(Role.ROLE_NATION)
                .build();

        List<Member> memberList = memberRepository.findAllByCountry_CountryNameAndAuthoritiesIn(memberRepository
                .findByEmail(email).getCountry().getCountryName(), Collections.singleton(authority), Sort.by(Sort.Direction.DESC, "property"));

        List<ManageResponseDto> manageResponseDtos = memberList.stream()
                .map(ManageResponseDto::new)
                .collect(Collectors.toList());

        manageResponseDtos.forEach(manage -> manage.setJobList(jobRepository.findAllJobName(memberRepository.findByEmail(email).getCountry().getCountryName())));
        return manageResponseDtos;
    }

    // 국민관리 회원 수정
    public ManageResponseDto modMembers(Long memberId, ManageUpdateRequestDto manageUpdateRequestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("찾으시는 회원이 없습니다."));

        member.update(jobRepository.findByJobName(manageUpdateRequestDto.getJob().getJobName()));
        memberRepository.save(member);

        return new ManageResponseDto(member);
    }

    // 국민관리 회원 삭제
    public void delMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    // 국민관리 회원 검색
    public List<ManageResponseDto> searchMembers(String searchWord, String email) {
        Authority authority = Authority.builder()
                .authorityName(Role.ROLE_NATION)
                .build();
        List<Member> memberList = memberRepository
                .findByCountry_CountryNameAndNicknameContainingAndAuthoritiesIn(memberRepository.findByEmail(email).getCountry().getCountryName(), searchWord, Collections.singleton(authority), Sort.by(Sort.Direction.DESC, "property"));
        return memberList.stream().map(ManageResponseDto::new).collect(Collectors.toList());
    }
    
    // 국민관리 월급 지급
    public ManageResponseDto payMembers(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("찾으시는 회원이 없습니다."));
        Account account = accountRepository.findByMember_Email(member.getEmail());

        account.changeAccountTotal(member.getAccount().getAccountTotal() + member.getJob().getPayCheck());
        accountRepository.save(account);

        AccountHistory accountHistory = AccountHistory.builder()
                .content("월급")
                .count(0L)
                .total(member.getJob().getPayCheck() * (100 - member.getCountry().getTax()) / 100)
                .stateName(StateName.DEPOSIT)
                .account(account)
                .build();
        accountHistoryRepository.save(accountHistory);

        if (member.getJob() == null) {
            member.changeProperty(0L);
        } else {
            member.changeProperty(member.getAccount().getAccountTotal()
                    + member.getSavings().getSavingsTotal()
                    + member.getStock().getStockTotal());
        }
        memberRepository.save(member);

        return new ManageResponseDto(member);
    }
}
