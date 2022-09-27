package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findAllByMember_Email(String email);

    List<Stock> findAllByMember_Country_CountryName(String countryName);
}
