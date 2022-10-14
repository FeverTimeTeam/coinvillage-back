package com.fevertime.coinvillage.domain.stock.repository;

import com.fevertime.coinvillage.domain.stock.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findAllByMember_Email(String email);

    List<Stock> findAllByMember_Country_CountryName(String countryName);
}
