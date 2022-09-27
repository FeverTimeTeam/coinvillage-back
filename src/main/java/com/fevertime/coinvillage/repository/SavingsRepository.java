package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.savings.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavingsRepository extends JpaRepository<Savings, Long> {

}
