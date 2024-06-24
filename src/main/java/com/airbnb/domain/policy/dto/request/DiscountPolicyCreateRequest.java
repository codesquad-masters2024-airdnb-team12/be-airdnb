package com.airbnb.domain.policy.dto.request;

import com.airbnb.domain.policy.entity.DiscountPolicy;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class DiscountPolicyCreateRequest {

    @NotNull
    @Min(0)
    private Integer initialDiscountCnt;

    @NotNull
    @Min(0)
    @Max(1)
    private Double initialDiscountRate;

    @NotNull
    @Min(0)
    @Max(1)
    private Double weeklyDiscountRate;

    @NotNull
    @Min(0)
    @Max(1)
    private Double monthlyDiscountRate;

    @NotNull
    private LocalDate startDate;
    private LocalDate endDate;

    public DiscountPolicy toEntity() {
        return DiscountPolicy.builder()
                .initialDiscountCnt(initialDiscountCnt)
                .initialDiscountRate(initialDiscountRate)
                .weeklyDiscountRate(weeklyDiscountRate)
                .monthlyDiscountRate(monthlyDiscountRate)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    public boolean isEndTimeAfterStartTime() {
        if(endDate == null) {
            return true;
        }

        return endDate.isAfter(startDate);
    }
}