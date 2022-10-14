package com.fevertime.coinvillage.domain.country.repository;

import com.fevertime.coinvillage.domain.country.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Country findByCountryName(String countryName);
}
