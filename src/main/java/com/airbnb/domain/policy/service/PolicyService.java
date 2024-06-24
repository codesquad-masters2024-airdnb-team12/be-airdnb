package com.airbnb.domain.policy.service;

import com.airbnb.domain.policy.dto.request.FeePolicyCreateRequest;
import com.airbnb.domain.policy.dto.response.FeePolicyResponse;
import com.airbnb.domain.policy.entity.FeePolicy;
import com.airbnb.domain.policy.repository.DiscountPolicyRepository;
import com.airbnb.domain.policy.repository.FeePolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PolicyService {

    private final FeePolicyRepository feePolicyRepository;
    private final DiscountPolicyRepository discountPolicyRepository;

    public FeePolicyResponse create(FeePolicyCreateRequest request) {
        validateCreateRequest(request);

        FeePolicy entity = request.toEntity();
        updatePreviousFeePolicyEndDate(entity);

        FeePolicy feePolicy = feePolicyRepository.save(entity);
        return FeePolicyResponse.of(feePolicy);
    }

    private void validateCreateRequest(FeePolicyCreateRequest request) {
        // endDate가 startDate 이후거나 null이여야 함
        if (!request.isEndTimeAfterStartTime()) {
            throw new IllegalArgumentException("마감일은 시작일 이후여야 합니다.");
        }
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
}
