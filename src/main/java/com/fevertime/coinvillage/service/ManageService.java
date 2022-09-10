package com.fevertime.coinvillage.service;

import com.fevertime.coinvillage.domain.account.Account;
import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.domain.model.StateName;
import com.fevertime.coinvillage.dto.manage.ManageResponseDto;
import com.fevertime.coinvillage.dto.manage.ManageUpdateRequestDto;
import com.fevertime.coinvillage.repository.AccountRepository;
import com.fevertime.coinvillage.repository.JobRepository;
import com.fevertime.coinvillage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ManageService {
    private final MemberRepository memberRepository;
    private final JobRepository jobRepository;
    private final AccountRepository accountRepository;

    // 국민관리 회원 전체보기
    public List<ManageResponseDto> showMembers() {
        List<Member> memberList = memberRepository.findAll(Sort.by(Sort.Direction.DESC, "property"));
        List<ManageResponseDto> manageResponseDtos = memberList.stream().map(ManageResponseDto::new).collect(Collectors.toList());
        manageResponseDtos.forEach(manage -> manage.setJobList(jobRepository.findAllJobName()));
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
    public List<ManageResponseDto> searchMembers(String searchWord) {
        List<Member> memberList = memberRepository.findByNicknameContaining(searchWord);
        return memberList.stream().map(ManageResponseDto::new).collect(Collectors.toList());
    }
    
    // 국민관리 월급 지급
    public ManageResponseDto payMembers(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("찾으시는 회원이 없습니다."));

        if (member.getJob() == null) {
            member.plusPay(0L);
        } else {
            member.plusPay(member.getJob().getPayCheck());
        }
        // log.info(String.valueOf(member.getAccountList().get(0).getAccountTotal() + member.getAccountList().get(0).getSavingsList().get(0).getSavingsTotal()));

        Account account = Account.builder()
                .content("월급")
                .count(0L)
                .total(member.getJob().getPayCheck())
                .stateName(StateName.DEPOSIT)
                .member(member)
                .build();

        memberRepository.save(member);
        accountRepository.save(account);

        return new ManageResponseDto(member);
    }
}
