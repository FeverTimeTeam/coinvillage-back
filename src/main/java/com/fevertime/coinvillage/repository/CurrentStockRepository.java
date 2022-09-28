package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.stock.CurrentStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CurrentStockRepository extends JpaRepository<CurrentStock, Long> {
    List<CurrentStock> findAllByStock_Member_Email(String email);

    List<CurrentStock> findAllByContentAndStock_Member_Country_CountryName(String content, String countryName);

    boolean existsByContentAndStock_Member_Country_CountryName(String content, String countryName);

    boolean existsByStock_Member_Email(String email);

    CurrentStock findByStock_Member_Email(String email);
}
