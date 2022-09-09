package com.fevertime.coinvillage.domain;

import com.fevertime.coinvillage.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Member> memberList;
}
