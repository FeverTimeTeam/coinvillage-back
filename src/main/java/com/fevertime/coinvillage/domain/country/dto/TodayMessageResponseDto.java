package com.fevertime.coinvillage.domain.country.dto;

import com.fevertime.coinvillage.domain.country.entity.TodayMessage;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodayMessageResponseDto {
    private Long todayMessageId;

    private String message;

    private LocalDateTime updatedAt;

    public TodayMessageResponseDto(TodayMessage todayMessage) {
        this.todayMessageId = todayMessage.getTodayMessageId();
        this.message = todayMessage.getMessage();
        this.updatedAt = todayMessage.getUpdatedAt();
    }
}
