package com.fevertime.coinvillage.service;

import com.fevertime.coinvillage.domain.Member;
import com.fevertime.coinvillage.dto.manage.ManageResponseDto;
import com.fevertime.coinvillage.dto.manage.ManageUpdateRequestDto;
import com.fevertime.coinvillage.repository.JobRepository;
import com.fevertime.coinvillage.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManageService {
    private final MemberRepository memberRepository;
    private final JobRepository jobRepository;

    // 국민관리 회원 전체보기
    public List<ManageResponseDto> showMembers() {
        List<Member> memberList = memberRepository.findAll(Sort.by(Sort.Direction.DESC, "property"));
        List<ManageResponseDto> manageResponseDtos = memberList.stream().map(ManageResponseDto::new).collect(Collectors.toList());
        manageResponseDtos.forEach(manage -> manage.setJobList(jobRepository.findAllJobName()));
        return manageResponseDtos;
    }

    public ManageResponseDto modMembers(Long memberId, ManageUpdateRequestDto manageUpdateRequestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException(""));

        member.update(jobRepository.findByJobName(manageUpdateRequestDto.getJob().getJobName()));

        memberRepository.save(member);

        return new ManageResponseDto(member);
    }
}
