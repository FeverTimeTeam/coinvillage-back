package com.fevertime.coinvillage.domain.country.entity;

import com.fevertime.coinvillage.domain.country.entity.Country;
import com.fevertime.coinvillage.global.config.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TodayMessage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todayMessageId;

    private String message;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToOne
    private Country country;

    public void changeMessage(String message) {
        this.message = message;
    }
}
