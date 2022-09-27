package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.savings.SavingsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavingsHistoryRepository extends JpaRepository<SavingsHistory, Long> {
    List<SavingsHistory> findBySavings_Member_Email(String email);
}
