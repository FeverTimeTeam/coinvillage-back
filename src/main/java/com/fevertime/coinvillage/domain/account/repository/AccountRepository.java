package com.fevertime.coinvillage.domain.account.repository;

import com.fevertime.coinvillage.domain.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByMember_Email(String email);

    Account findByMember_Email(String email);
}
