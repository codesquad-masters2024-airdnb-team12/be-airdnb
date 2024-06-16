package com.airbnb.domain.policy.entity;

import com.airbnb.domain.common.BaseTime;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountPolicy extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_policy_id")
    private Long id;
    private int initialDiscountCnt;
    private double initialDiscountRate;
    private double weeklyDiscountRate;
    private double monthlyDiscountRate;

    @Column(nullable = false)
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    private DiscountPolicy(int initialDiscountCnt, double initialDiscountRate, double weeklyDiscountRate, double monthlyDiscountRate, LocalDate startDate, LocalDate endDate) {
        this.initialDiscountCnt = initialDiscountCnt;
        this.initialDiscountRate = initialDiscountRate;
        this.weeklyDiscountRate = weeklyDiscountRate;
        this.monthlyDiscountRate = monthlyDiscountRate;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
