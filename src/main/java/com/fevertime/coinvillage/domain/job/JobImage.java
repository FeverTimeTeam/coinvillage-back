package com.fevertime.coinvillage.domain.job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobImageId;

    private String jobImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Job job;
}
