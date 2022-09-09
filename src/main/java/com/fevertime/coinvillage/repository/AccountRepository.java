package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.account.Account;
import com.fevertime.coinvillage.dto.account.AccountResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByMember_Email(String email);
}
