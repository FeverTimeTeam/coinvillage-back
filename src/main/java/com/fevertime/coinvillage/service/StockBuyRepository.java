package com.fevertime.coinvillage.service;

import com.fevertime.coinvillage.domain.account.StockBuy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockBuyRepository extends JpaRepository<StockBuy, Long> {
}
