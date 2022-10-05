package com.fevertime.coinvillage.controller.api;

import com.fevertime.coinvillage.domain.member.Member;
import com.fevertime.coinvillage.dto.login.*;
import com.fevertime.coinvillage.jwt.JwtFilter;
import com.fevertime.coinvillage.jwt.TokenProvider;
import com.fevertime.coinvillage.repository.MemberRepository;
import com.fevertime.coinvillage.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("members")
@Api(tags = "로그인, 회원가입")
public class MemberApiController {
    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @PostMapping("ruler/signup")
    @ApiOperation(value = "선생님 회원가입")
    public ResponseEntity<MemberResponseDto> signupRuler(
        @Valid @RequestBody MemberRequestDto memberRequestDto
    ) {
        return ResponseEntity.ok(memberService.signupRuler(memberRequestDto));
    }

    @PostMapping("nation/signup")
    @ApiOperation(value = "학생 회원가입")
    public ResponseEntity<MemberResponseDto> signupNation(
            @Valid @RequestBody MemberRequestDto memberRequestDto
    ) {
        return ResponseEntity.ok(memberService.signupNation(memberRequestDto));
    }

    @PostMapping("authenticate")
    @ApiOperation(value = "로그인")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();

        String accessToken = tokenProvider.createToken(authentication);
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, accessToken);

        Member member = memberRepository.findByEmail(loginDto.getEmail());
        MemberResponseDto memberResponseDto = new MemberResponseDto(member);

        return new ResponseEntity<>(new TokenDto(accessToken, memberResponseDto), httpHeaders, HttpStatus.OK);
    }
    
    @GetMapping
    @ApiOperation(value = "회원 전체보기 테스트")
    public ResponseEntity<List<CountryResponseDto>> findCountries() {
        return ResponseEntity.ok(memberService.findCountries());
    }

    @PutMapping("profile")
    @ApiOperation(value = "프로필 수정")
    public ResponseEntity<MemberResponseDto> changeProfile(@RequestPart MultipartFile file, Authentication authentication) {
        return ResponseEntity.ok(memberService.changeProfileImage(file, authentication.getName()));
    }

    @GetMapping("profile")
    @ApiOperation(value = "프로필 이미지정보 가져오기")
    public ResponseEntity<MemberResponseDto> getProfile(Authentication authentication) {
        return ResponseEntity.ok(memberService.getProfileImage(authentication.getName()));
    }

    @GetMapping("property")
    @ApiOperation(value = "현재 재산")
    public ResponseEntity<PropertryResponseDto> getProperty(Authentication authentication) {
        return ResponseEntity.ok(memberService.getPropertry(authentication.getName()));
    }

    @GetMapping("memberInfo")
    @ApiOperation(value = "회원 정보 가져오기")
    public ResponseEntity<MemberResponseDto> getMemberInfo(Authentication authentication) {
        return ResponseEntity.ok(memberService.getMemberInfo(authentication.getName()));
    }

    @GetMapping("test")
    @ApiOperation(value = "무중단 배포 테스트 연결용")
    public ResponseEntity<String> showTest() {
        return ResponseEntity.ok("테스트");
    }
}
