package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.account.SavingsSetting;
import com.fevertime.coinvillage.domain.model.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsSettingRepository extends JpaRepository<SavingsSetting, Long> {
    SavingsSetting findAllByTerm(Term term);

    SavingsSetting findByMember_Email(String email);
}
