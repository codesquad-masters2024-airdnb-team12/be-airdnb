package com.airbnb.domain.policy.repository;

import com.airbnb.domain.policy.entity.FeePolicy;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeePolicyRepository extends JpaRepository<FeePolicy, Long> {
    Optional<FeePolicy> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDate baseDate);
}