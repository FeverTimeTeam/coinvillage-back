package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.account.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
}
