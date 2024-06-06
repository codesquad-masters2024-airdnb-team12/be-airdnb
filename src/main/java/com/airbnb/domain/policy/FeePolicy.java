package com.airbnb.domain.policy;

import com.airbnb.domain.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Builder
    private FeePolicy(double hostFeeRate, double guestFeeRate, LocalDateTime startTime, LocalDateTime endTime) {
        this.hostFeeRate = hostFeeRate;
        this.guestFeeRate = guestFeeRate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
