package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.account.StockBuy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockBuyRepository extends JpaRepository<StockBuy, Long> {
}
