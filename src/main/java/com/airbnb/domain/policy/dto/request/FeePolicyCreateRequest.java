package com.airbnb.domain.policy.dto.request;

import com.airbnb.domain.policy.entity.FeePolicy;
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
public class FeePolicyCreateRequest {

    @NotNull
    @Min(0)
    @Max(100)
    private Double hostFeeRate;

    @NotNull
    @Min(0)
    @Max(100)
    private Double guestFeeRate;

    @NotNull
    private LocalDate startDate;
    private LocalDate endDate;

    public FeePolicy toEntity() {
        return FeePolicy.builder()
                .hostFeeRate(hostFeeRate)
                .guestFeeRate(guestFeeRate)
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
