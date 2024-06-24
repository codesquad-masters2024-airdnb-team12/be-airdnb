package com.airbnb.domain.policy.dto.response;

import com.airbnb.domain.policy.entity.FeePolicy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class FeePolicyListResponse {

    List<FeePolicyResponse> feePolicies;

    public static FeePolicyListResponse of(List<FeePolicy> feePolicies) {
        return new FeePolicyListResponse(
                feePolicies.stream().map(FeePolicyResponse::of).toList());
    }
}