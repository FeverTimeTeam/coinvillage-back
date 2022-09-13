package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    int countByJobName(String jobName);

    @Query("select j.jobName from Job j")
    List<String> findAllJobName();

    Job findByJobName(String jobName);

    List<Job> findAllByCountry_CountryName(String email);
}
