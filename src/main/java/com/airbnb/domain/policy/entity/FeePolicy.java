package com.airbnb.domain.policy.entity;

import com.airbnb.domain.common.BaseTime;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeePolicy extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fee_policy_id")
    private Long id;
    private double hostFeeRate;
    private double guestFeeRate;

    @Column(nullable = false)
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    private FeePolicy(double hostFeeRate, double guestFeeRate, LocalDate startDate, LocalDate endDate) {
        this.hostFeeRate = hostFeeRate;
        this.guestFeeRate = guestFeeRate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateEndDate(LocalDate newStartDate) {
        this.endDate = newStartDate;
    }
}
