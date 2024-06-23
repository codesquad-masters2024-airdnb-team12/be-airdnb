package com.airbnb.domain.policy.repository;

import com.airbnb.domain.policy.entity.FeePolicy;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeePolicyRepository extends JpaRepository<FeePolicy, Long> {

    @Query("SELECT fp FROM FeePolicy fp WHERE " +
            "(:currentDate BETWEEN fp.startDate AND COALESCE(fp.endDate, :currentDate))")
    Optional<FeePolicy> findValidFeePolicy(@Param("currentDate") LocalDate currentDate);

    Optional<FeePolicy> findTopByOrderByIdDesc();
}