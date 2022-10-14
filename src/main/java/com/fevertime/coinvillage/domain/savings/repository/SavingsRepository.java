package com.fevertime.coinvillage.domain.savings.repository;

import com.fevertime.coinvillage.domain.savings.entity.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsRepository extends JpaRepository<Savings, Long> {

}
