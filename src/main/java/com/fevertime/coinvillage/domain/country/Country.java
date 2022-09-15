package com.fevertime.coinvillage.domain.country;

import com.fevertime.coinvillage.domain.member.Member;
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

    @OneToOne(mappedBy = "country")
    private TodayMessage todayMessage;

    public void changeTax(Long tax) {
        this.tax = tax;
    }
}
