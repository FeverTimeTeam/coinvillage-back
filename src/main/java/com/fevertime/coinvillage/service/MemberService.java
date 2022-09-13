package com.fevertime.coinvillage.service;

import com.fevertime.coinvillage.domain.country.Country;
import com.fevertime.coinvillage.domain.job.Job;
import com.fevertime.coinvillage.domain.account.Account;
import com.fevertime.coinvillage.domain.member.Authority;
import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.dto.login.CountryResponseDto;
import com.fevertime.coinvillage.dto.login.MemberRequestDto;
import com.fevertime.coinvillage.dto.login.MemberResponseDto;
import com.fevertime.coinvillage.dto.login.MemberUpdateRequestDto;
import com.fevertime.coinvillage.exception.DuplicateMemberException;
import com.fevertime.coinvillage.repository.*;
import com.fevertime.coinvillage.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.fevertime.coinvillage.domain.model.Role.ROLE_RULER;
import static com.fevertime.coinvillage.domain.model.Role.ROLE_NATION;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final CountryRepository countryRepository;
    private final JobRepository jobRepository;
    private final AccountRepository accountRepository;
    private final SavingsRepository savingsRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Uploader s3Uploader;

    // 선생님 회원가입
    @Transactional
    public MemberResponseDto signupRuler(MemberRequestDto memberRequestDto) {
        if (memberRepository.findOneWithAuthoritiesByEmail(memberRequestDto.getEmail()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        countryRepository.save(memberRequestDto.toCountry());

        Country country = countryRepository.findByCountryName(memberRequestDto.getCountryName());

        memberRequestDto.setCountry(country);

        Authority authority = Authority.builder()
                .authorityName(ROLE_RULER)
                .build();

        Member member = Member.builder()
                .email(memberRequestDto.getEmail())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .nickname(memberRequestDto.getNickname())
                .activated(true)
                .phoneNumber(memberRequestDto.getPhoneNumber())
                .authorities(Collections.singleton(authority))
                .country(country)
                .build();
        memberRepository.save(member);

        return new MemberResponseDto(member);
    }

    // 학생 회원가입
    @Transactional
    public MemberResponseDto signupNation(MemberRequestDto memberRequestDto) {
        if (memberRepository.findOneWithAuthoritiesByEmail(memberRequestDto.getEmail()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        if (countryRepository.findByCountryName(memberRequestDto.getCountryName()) == null) {
            throw new IllegalArgumentException("존재하지 않는 국가입니다.");
        }

        Country country = countryRepository.findByCountryName(memberRequestDto.getCountryName());

        memberRequestDto.setCountry(country);

        Authority authority = Authority.builder()
                .authorityName(ROLE_NATION)
                .build();

        Member member = Member.builder()
                .email(memberRequestDto.getEmail())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .nickname(memberRequestDto.getNickname())
                .activated(true)
                .property(0L)
                .authorities(Collections.singleton(authority))
                .phoneNumber(memberRequestDto.getPhoneNumber())
                .country(country)
                .savingsTotal(0L)
                .stockTotal(0L)
                .job(null)
                .imageUrl(null)
                .build();

        Account account = Account.builder()
                .accountTotal(0L)
                .member(member)
                .build();

        memberRepository.save(member);
        accountRepository.save(account);

        return new MemberResponseDto(member);
    }

    // 테스트용 전체보기 리스트
    public List<CountryResponseDto> findCountries() {
        List<Country> countries = countryRepository.findAll();
        return countries.stream().map(CountryResponseDto::new).collect(Collectors.toList());
    }

    // 직업 담당자 수정
    @Transactional
    public Long modJob(Long jobId, MemberUpdateRequestDto memberUpdateRequestDto) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new IllegalArgumentException("해당하는 직업이 없습니다."));

        for (int i = 0; i < memberUpdateRequestDto.getMemberList().size(); i++) {
            Member member = memberRepository.findByNickname(memberUpdateRequestDto.getMemberList().get(i));
            member.setJob(job);
            memberRepository.save(member);
        }

        return job.getJobId();
    }

    // 이미지 수정
    @Transactional
    public MemberResponseDto changeProfileImage(MultipartFile file, String email) {
        Member member = memberRepository.findByEmail(email);

        try {
            String S3Url = s3Uploader.upload(file, "profile");
            member.changeProfileImg(S3Url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        memberRepository.save(member);
        return new MemberResponseDto(member);
    }
    
    // 프로필 정보주기
    @Transactional
    public MemberResponseDto getProfileImage(String email) {
        Member member = memberRepository.findByEmail(email);
        return new MemberResponseDto(member);
    }
}
