package com.fevertime.coinvillage.domain.job.repository;

import com.fevertime.coinvillage.domain.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("select j.jobName from Job j where j.country.countryName = ?1")
    List<String> findAllJobName(String email);

    Job findByJobName(String jobName);

    List<Job> findAllByCountry_CountryName(String email);
}
