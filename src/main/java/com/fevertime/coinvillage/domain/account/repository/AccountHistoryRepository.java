package com.fevertime.coinvillage.domain.account.repository;

import com.fevertime.coinvillage.domain.account.entity.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {
    List<AccountHistory> findAllByAccount_Member_Email(String email);
}
