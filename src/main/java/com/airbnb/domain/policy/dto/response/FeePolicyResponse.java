package com.airbnb.domain.policy.dto.response;

import com.airbnb.domain.policy.entity.FeePolicy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class FeePolicyResponse {

    private Long id;
    private Double hostFeeRate;
    private Double guestFeeRate;
    private LocalDate startTime;
    private LocalDate endTime;

    public static FeePolicyResponse of(FeePolicy feePolicy) {
        return FeePolicyResponse.builder()
                .id(feePolicy.getId())
                .hostFeeRate(feePolicy.getHostFeeRate())
                .guestFeeRate(feePolicy.getGuestFeeRate())
                .startTime(feePolicy.getStartDate())
                .endTime(feePolicy.getEndDate())
                .build();
    }
}
