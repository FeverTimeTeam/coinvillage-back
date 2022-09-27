package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.account.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {
    List<AccountHistory> findAllByAccount_Member_Email(String email);
}
