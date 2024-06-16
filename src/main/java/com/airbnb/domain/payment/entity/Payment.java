package com.airbnb.domain.payment.entity;

import com.airbnb.domain.booking.entity.Booking;
import com.airbnb.domain.common.BaseTime;
import com.airbnb.domain.payment.dto.AmountResult;
import com.airbnb.domain.policy.entity.DiscountPolicy;
import com.airbnb.domain.policy.entity.FeePolicy;
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
    @Column(nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private Card card;

    @Column(updatable = false)
    private int totalAmount;

    @Column(updatable = false)
    private int feeAmount;

    @Column(updatable = false)
    private int discountAmount;

    @Column(updatable = false)
    private int finalAmount;

    @Builder
    private Payment(Booking booking, AmountResult amountResult) {
        this.booking = booking;
        this.feePolicy = amountResult.getFeePolicy();
        this.discountPolicy = amountResult.getDiscountPolicy();
        this.status = PaymentStatus.PENDING;
        this.totalAmount = amountResult.getTotalAmount();
        this.feeAmount = amountResult.getGuestFeeAmount();
        this.discountAmount = amountResult.getDiscountAmount();
        this.finalAmount = amountResult.getFinalAmount();
    }

    @Builder
    private Payment(Booking booking, FeePolicy feePolicy, DiscountPolicy discountPolicy, PaymentStatus status, Card card, int totalAmount, int feeAmount, int discountAmount, int finalAmount) {
        this.booking = booking;
        this.feePolicy = feePolicy;
        this.discountPolicy = discountPolicy;
        this.status = status;
        this.card = card;
        this.totalAmount = totalAmount;
        this.feeAmount = feeAmount;
        this.discountAmount = discountAmount;
        this.finalAmount = finalAmount;
    }
}
