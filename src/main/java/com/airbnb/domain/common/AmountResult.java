package com.airbnb.domain.common;

import com.airbnb.domain.policy.entity.DiscountPolicy;
import com.airbnb.domain.policy.entity.FeePolicy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AmountResult {

    private FeePolicy feePolicy;
    private DiscountPolicy discountPolicy;
    private int totalAmount;
    private int hostFeeAmount;
    private int guestFeeAmount;
    private int discountAmount;
    private int finalAmount;

}