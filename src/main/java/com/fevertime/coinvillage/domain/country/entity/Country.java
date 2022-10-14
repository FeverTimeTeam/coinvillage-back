package com.fevertime.coinvillage.domain.country.entity;

import com.fevertime.coinvillage.domain.job.entity.Job;
import com.fevertime.coinvillage.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long countryId;

    private String countryName;

    private Long tax;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @BatchSize(size = 10)
    private List<Member> memberList;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Job> jobList;

    @OneToOne(mappedBy = "country")
    private TodayMessage todayMessage;

    public void changeTax(Long tax) {
        this.tax = tax;
    }
}
