package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.account.StockBuy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockBuyRepository extends JpaRepository<StockBuy, Long> {
    List<StockBuy> findAllByStock_Member_Email(String email);
}
