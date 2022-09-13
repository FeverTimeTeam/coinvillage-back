package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.country.TodayMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodayMessageRepository extends JpaRepository<TodayMessage, Long> {
}
