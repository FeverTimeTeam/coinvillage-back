package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.account.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsRepository extends JpaRepository<Savings, Long> {
}
