package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.account.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findAllByMember_Email(String email);

    Stock findByMember_Email(String email);
}
