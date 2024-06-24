package com.airbnb.domain.policy.dto.response;

import com.airbnb.domain.policy.entity.DiscountPolicy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class DiscountPolicyResponse {

    private Long id;
    private int initialDiscountCnt;
    private double initialDiscountRate;
    private double weeklyDiscountRate;
    private double monthlyDiscountRate;
    private LocalDate startDate;
    private LocalDate endDate;

    public static DiscountPolicyResponse of(DiscountPolicy discountPolicy) {
        return DiscountPolicyResponse.builder()
                .id(discountPolicy.getId())
                .initialDiscountCnt(discountPolicy.getInitialDiscountCnt())
                .initialDiscountRate(discountPolicy.getInitialDiscountRate())
                .weeklyDiscountRate(discountPolicy.getWeeklyDiscountRate())
                .monthlyDiscountRate(discountPolicy.getMonthlyDiscountRate())
                .startDate(discountPolicy.getStartDate())
                .endDate(discountPolicy.getEndDate())
                .build();
    }
}
