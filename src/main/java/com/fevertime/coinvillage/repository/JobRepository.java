package com.fevertime.coinvillage.repository;

import com.fevertime.coinvillage.domain.Job;
import com.fevertime.coinvillage.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    int countByJobName(String jobName);
}
