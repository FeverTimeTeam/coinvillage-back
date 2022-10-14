package com.fevertime.coinvillage.domain.country.repository;

import com.fevertime.coinvillage.domain.country.entity.TodayMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodayMessageRepository extends JpaRepository<TodayMessage, Long> {
}
