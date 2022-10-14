package com.fevertime.coinvillage.domain.stock.repository;

import com.fevertime.coinvillage.domain.stock.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
    List<StockHistory> findAllByMember_Email(String email);
}
