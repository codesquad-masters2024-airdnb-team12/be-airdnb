package com.airbnb.domain.policy;

import com.airbnb.domain.common.BaseTime;
import jakarta.persistence.*;
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
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Builder
    private DiscountPolicy(int initialDiscountCnt, double initialDiscountRate, double weeklyDiscountRate, double monthlyDiscountRate, LocalDateTime startTime, LocalDateTime endTime) {
        this.initialDiscountCnt = initialDiscountCnt;
        this.initialDiscountRate = initialDiscountRate;
        this.weeklyDiscountRate = weeklyDiscountRate;
        this.monthlyDiscountRate = monthlyDiscountRate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
