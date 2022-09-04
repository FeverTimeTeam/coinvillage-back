package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
    int countByJobName(String jobName);
}
