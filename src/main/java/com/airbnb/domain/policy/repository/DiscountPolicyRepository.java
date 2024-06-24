package com.airbnb.domain.policy.repository;

import com.airbnb.domain.policy.entity.DiscountPolicy;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiscountPolicyRepository extends JpaRepository<DiscountPolicy, Long> {

    @Query("SELECT dp FROM DiscountPolicy dp WHERE " +
            "(:currentDate BETWEEN dp.startDate AND COALESCE(dp.endDate, :currentDate))")
    Optional<DiscountPolicy> findValidDiscountPolicy(@Param("currentDate") LocalDate currentDate);

    Optional<DiscountPolicy> findTopByOrderByIdDesc();
}