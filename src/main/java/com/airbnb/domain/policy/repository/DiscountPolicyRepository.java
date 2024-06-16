package com.airbnb.domain.policy.repository;

import com.airbnb.domain.policy.entity.DiscountPolicy;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountPolicyRepository extends JpaRepository<DiscountPolicy, Long> {
    Optional<DiscountPolicy> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate now);
}