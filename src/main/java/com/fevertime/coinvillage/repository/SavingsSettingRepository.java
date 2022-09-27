package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.member.Authority;
import com.fevertime.coinvillage.domain.savings.SavingsSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface SavingsSettingRepository extends JpaRepository<SavingsSetting, Long> {
    SavingsSetting findBySavings_Member_Email(String email);

    List<SavingsSetting> findAllBySavings_Member_Country_CountryNameAndSavings_Member_AuthoritiesIn(String countryName, Set<Authority> authorities);
}
