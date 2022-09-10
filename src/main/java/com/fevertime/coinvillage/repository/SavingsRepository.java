package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.account.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavingsRepository extends JpaRepository<Savings, Long> {
   List<Savings> findByAccount_Member_Email(String email);
}
