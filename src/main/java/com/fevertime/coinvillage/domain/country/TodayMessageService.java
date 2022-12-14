package com.fevertime.coinvillage.domain.country;

import com.fevertime.coinvillage.domain.country.entity.TodayMessage;
import com.fevertime.coinvillage.domain.country.dto.TodayMessageRequestDto;
import com.fevertime.coinvillage.domain.country.dto.TodayMessageResponseDto;
import com.fevertime.coinvillage.domain.country.repository.TodayMessageRepository;
import com.fevertime.coinvillage.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodayMessageService {
    private final TodayMessageRepository todayMessageRepository;
    private final MemberRepository memberRepository;

    // 오늘의 정보 수정
    @Transactional
    public TodayMessageResponseDto makeInfo(String email, TodayMessageRequestDto todayMessageRequestDto) {
        TodayMessage todayMessage = todayMessageRepository.findById(memberRepository.findByEmail(email).getCountry().getTodayMessage().getTodayMessageId()).orElseThrow(() -> new IllegalArgumentException("해당 정보가 없습니다."));
        todayMessage.changeMessage(todayMessageRequestDto.getMessage());
        todayMessageRepository.save(todayMessage);
        return new TodayMessageResponseDto(todayMessage);
    }

    // 오늘의 정보 가져오기
    @Transactional(readOnly = true)
    public TodayMessageResponseDto showInfo(String email) {
        TodayMessage todayMessage = todayMessageRepository.findById(memberRepository.findByEmail(email).getCountry().getTodayMessage().getTodayMessageId()).orElseThrow(() -> new IllegalArgumentException("해당 정보가 없습니다."));
        return new TodayMessageResponseDto(todayMessage);
    }
}
