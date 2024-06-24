package com.airbnb.domain.policy.service;

import com.airbnb.domain.policy.dto.request.DiscountPolicyCreateRequest;
import com.airbnb.domain.policy.dto.request.FeePolicyCreateRequest;
import com.airbnb.domain.policy.dto.response.DiscountPolicyListResponse;
import com.airbnb.domain.policy.dto.response.DiscountPolicyResponse;
import com.airbnb.domain.policy.dto.response.FeePolicyListResponse;
import com.airbnb.domain.policy.dto.response.FeePolicyResponse;
import com.airbnb.domain.policy.entity.DiscountPolicy;
import com.airbnb.domain.policy.entity.FeePolicy;
import com.airbnb.domain.policy.repository.DiscountPolicyRepository;
import com.airbnb.domain.policy.repository.FeePolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PolicyService {

    private final FeePolicyRepository feePolicyRepository;
    private final DiscountPolicyRepository discountPolicyRepository;

    @Transactional
    public FeePolicyResponse createFeePolicy(FeePolicyCreateRequest request) {
        if (!request.isEndTimeAfterStartTime()) {
            throw new IllegalArgumentException("마감일은 시작일 이후여야 합니다.");
        }

        if (!request.getStartDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("시작일은 현재 이후여야 합니다.");
        }

        FeePolicy entity = request.toEntity();
        updatePreviousFeePolicyEndDate(entity);

        FeePolicy feePolicy = feePolicyRepository.save(entity);
        return FeePolicyResponse.of(feePolicy);
    }

    @Transactional
    public DiscountPolicyResponse createDiscountPolicy(DiscountPolicyCreateRequest request) {
        if(!request.isEndTimeAfterStartTime()) {
            throw new IllegalArgumentException("마감일은 시작일 이후여야 합니다.");
        }

        if (!request.getStartDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("시작일은 현재 이후여야 합니다.");
        }

        DiscountPolicy entity = request.toEntity();
        updatePreviousDiscountPolicyEndDate(entity);

        DiscountPolicy discountPolicy = discountPolicyRepository.save(entity);
        return DiscountPolicyResponse.of(discountPolicy);
    }

    public FeePolicyListResponse getFeePolicyList() {
        List<FeePolicy> feePolicies = feePolicyRepository.findAll();
        return FeePolicyListResponse.of(feePolicies);
    }

    public DiscountPolicyListResponse getDiscountPolicyList() {
        List<DiscountPolicy> discountPolicies = discountPolicyRepository.findAll();
        return DiscountPolicyListResponse.of(discountPolicies);
    }

    private void updatePreviousFeePolicyEndDate(FeePolicy feePolicy) {
        feePolicyRepository.findTopByOrderByIdDesc().ifPresent(
                previousPolicy -> {
                    if (!previousPolicy.getStartDate().isBefore(feePolicy.getStartDate())) {
                        throw new IllegalArgumentException("시작일은 이전 수수료 정책 시작일 이후여야 합니다.");
                    }

                    previousPolicy.updateEndDate(feePolicy.getStartDate());
                }
        );
    }

    private void updatePreviousDiscountPolicyEndDate(DiscountPolicy discountPolicy) {
        discountPolicyRepository.findTopByOrderByIdDesc().ifPresent(
                previousPolicy -> {
                    if (!previousPolicy.getStartDate().isBefore(discountPolicy.getStartDate())) {
                        throw new IllegalArgumentException("시작일은 이전 수수료 정책 시작일 이후여야 합니다.");
                    }

                    previousPolicy.updateEndDate(discountPolicy.getStartDate());
                }
        );
    }
}
