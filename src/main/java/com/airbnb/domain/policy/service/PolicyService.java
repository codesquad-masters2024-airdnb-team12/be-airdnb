package com.airbnb.domain.policy.service;

import com.airbnb.domain.policy.entity.DiscountPolicy;
import com.airbnb.domain.policy.entity.FeePolicy;
import com.airbnb.domain.policy.repository.DiscountPolicyRepository;
import com.airbnb.domain.policy.repository.FeePolicyRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PolicyService {

    private final FeePolicyRepository feePolicyRepository;
    private final DiscountPolicyRepository discountPolicyRepository;

    public FeePolicy getFeePolicyByDate(LocalDate baseDate) {
        return feePolicyRepository.findFirstByStartDateLessThanEqualAndEndDateGreaterThanEqual(baseDate, baseDate)
            .orElseThrow();
    }

    public DiscountPolicy getDiscountPolicyByDate(LocalDate baseDate) {
        return discountPolicyRepository.findFirstByStartDateLessThanEqualAndEndDateGreaterThanEqual(baseDate, baseDate)
            .orElseThrow();
    }
}
