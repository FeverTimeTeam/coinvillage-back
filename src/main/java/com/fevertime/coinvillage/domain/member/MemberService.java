package com.fevertime.coinvillage.domain.member;

import com.fevertime.coinvillage.domain.account.repository.AccountRepository;
import com.fevertime.coinvillage.domain.country.repository.CountryRepository;
import com.fevertime.coinvillage.domain.country.repository.TodayMessageRepository;
import com.fevertime.coinvillage.domain.member.repository.MemberRepository;
import com.fevertime.coinvillage.domain.savings.repository.SavingsRepository;
import com.fevertime.coinvillage.domain.savings.repository.SavingsSettingRepository;
import com.fevertime.coinvillage.domain.savings.entity.Savings;
import com.fevertime.coinvillage.domain.savings.entity.SavingsSetting;
import com.fevertime.coinvillage.domain.country.entity.Country;
import com.fevertime.coinvillage.domain.country.entity.TodayMessage;
import com.fevertime.coinvillage.domain.account.entity.Account;
import com.fevertime.coinvillage.domain.member.entity.Authority;
import com.fevertime.coinvillage.domain.member.entity.Member;
import com.fevertime.coinvillage.domain.model.Term;
import com.fevertime.coinvillage.domain.stock.entity.Stock;
import com.fevertime.coinvillage.domain.member.dto.login.*;
import com.fevertime.coinvillage.domain.stock.repository.StockRepository;
import com.fevertime.coinvillage.global.error.ErrorCode;
import com.fevertime.coinvillage.global.error.exception.BadRequestException;
import com.fevertime.coinvillage.global.error.exception.DuplicateMemberException;
import com.fevertime.coinvillage.global.config.s3.S3Uploader;
import com.fevertime.coinvillage.global.error.exception.NotFoundException;
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
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TodayMessageRepository todayMessageRepository;
    private final SavingsSettingRepository savingsSettingRepository;
    private final SavingsRepository savingsRepository;
    private final StockRepository stockRepository;
    private final S3Uploader s3Uploader;

    // ????????? ????????????
    @Transactional
    public MemberResponseDto signupRuler(MemberRequestDto memberRequestDto) {
        if (memberRepository.findOneWithAuthoritiesByEmail(memberRequestDto.getEmail()).orElse(null) != null) {
            throw new BadRequestException(ErrorCode.MEMBER_ALREADY_EXIST);
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
                .property(null)
                .imageUrl(null)
                .job(null)
                .country(country)
                .build();
        memberRepository.save(member);

        Account account = Account.builder()
                .accountTotal(0L)
                .member(member)
                .build();
        accountRepository.save(account);

        Savings savings = Savings.builder()
                .savingsTotal(0L)
                .member(member)
                .build();
        savingsRepository.save(savings);

        Stock stock = Stock.builder()
                .stockTotal(0L)
                .member(member)
                .build();
        stockRepository.save(stock);

        TodayMessage todayMessage = TodayMessage.builder()
                .message(null)
                .country(country)
                .build();
        todayMessageRepository.save(todayMessage);

        return new MemberResponseDto(member);
    }

    // ?????? ????????????
    @Transactional
    public MemberResponseDto signupNation(MemberRequestDto memberRequestDto) {
        if (memberRepository.findOneWithAuthoritiesByEmail(memberRequestDto.getEmail()).orElse(null) != null) {
            throw new BadRequestException(ErrorCode.MEMBER_ALREADY_EXIST);
        }

        if (countryRepository.findByCountryName(memberRequestDto.getCountryName()) == null) {
            throw new NotFoundException(ErrorCode.COUNTRY_NOT_FOUND);
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
                .job(null)
                .imageUrl(null)
                .build();
        memberRepository.save(member);

        Account account = Account.builder()
                .accountTotal(0L)
                .member(member)
                .build();
        accountRepository.save(account);

        Savings savings = Savings.builder()
                .savingsTotal(0L)
                .member(member)
                .build();
        savingsRepository.save(savings);

        SavingsSetting savingsSetting = SavingsSetting.builder()
                .term(Term.MONTHLY)
                .day("1")
                .bill(0L)
                .maturity(6L)
                .interest(0L)
                .savings(savings)
                .build();
        savingsSettingRepository.save(savingsSetting);

        Stock stock = Stock.builder()
                .stockTotal(0L)
                .member(member)
                .build();
        stockRepository.save(stock);

        return new MemberResponseDto(member);
    }

    // ???????????? ???????????? ?????????
    @Transactional(readOnly = true)
    public List<CountryResponseDto> findCountries() {
        List<Country> countries = countryRepository.findAll();
        return countries.stream().map(CountryResponseDto::new).collect(Collectors.toList());
    }

    // ????????? ??????
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
    
    // ????????? ????????????
    @Transactional(readOnly = true)
    public MemberResponseDto getProfileImage(String email) {
        Member member = memberRepository.findByEmail(email);
        return new MemberResponseDto(member);
    }

    // ?????? ??????
    @Transactional(readOnly = true)
    public PropertryResponseDto getPropertry(String email) {
        Member member = memberRepository.findByEmail(email);
        return new PropertryResponseDto(member);
    }

    // ?????? ?????? ????????????
    @Transactional(readOnly = true)
    public MemberResponseDto getMemberInfo(String email) {
        Member member = memberRepository.findByEmail(email);
        return new MemberResponseDto(member);
    }
}
