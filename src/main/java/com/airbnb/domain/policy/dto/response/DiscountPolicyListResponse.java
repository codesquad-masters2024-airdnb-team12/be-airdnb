package com.airbnb.domain.policy.dto.response;

import com.airbnb.domain.policy.entity.DiscountPolicy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class DiscountPolicyListResponse {

    private List<DiscountPolicyResponse> discountPolicies;

    public static DiscountPolicyListResponse of(List<DiscountPolicy> discountPolicies) {
        return new DiscountPolicyListResponse(
                discountPolicies.stream().map(DiscountPolicyResponse::of).toList()
        );
    }
}
