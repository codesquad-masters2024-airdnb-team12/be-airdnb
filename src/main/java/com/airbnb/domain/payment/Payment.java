package com.airbnb.domain.payment;

import com.airbnb.domain.booking.Booking;
import com.airbnb.domain.common.BaseTime;
import com.airbnb.domain.policy.DiscountPolicy;
import com.airbnb.domain.policy.FeePolicy;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @OneToOne(mappedBy = "payment", fetch = FetchType.LAZY)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fee_policy_id", nullable = false, updatable = false)
    private FeePolicy feePolicy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_policy_id", nullable = false, updatable = false)
    private DiscountPolicy discountPolicy;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private Card card;

    @Column(updatable = false)
    private int totalAmount;

    @Column(updatable = false)
    private int pureAmount;

    @Column(updatable = false)
    private int discountAmount;

    @Column(updatable = false)
    private int feeAmount;

    @Builder
    private Payment(Booking booking, FeePolicy feePolicy, DiscountPolicy discountPolicy, PaymentStatus status, Card card, int totalAmount, int pureAmount, int discountAmount, int feeAmount) {
        this.booking = booking;
        this.feePolicy = feePolicy;
        this.discountPolicy = discountPolicy;
        this.status = status;
        this.card = card;
        this.totalAmount = totalAmount;
        this.pureAmount = pureAmount;
        this.discountAmount = discountAmount;
        this.feeAmount = feeAmount;
    }
}
