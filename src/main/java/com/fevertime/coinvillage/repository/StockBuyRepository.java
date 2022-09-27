package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.member.Authority;
import com.fevertime.coinvillage.domain.stock.StockBuy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface StockBuyRepository extends JpaRepository<StockBuy, Long> {
    List<StockBuy> findAllByStock_StockId(Long stockId);

    List<StockBuy> findAllByStock_Member_Country_CountryName(String countryName);

    StockBuy findByContent(String content);
}
