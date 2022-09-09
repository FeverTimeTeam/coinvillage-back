package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
