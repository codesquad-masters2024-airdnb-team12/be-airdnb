package com.airbnb.domain.payment.service;

import com.airbnb.domain.booking.entity.Booking;
import com.airbnb.domain.payment.dto.AmountResult;
import com.airbnb.domain.policy.entity.DiscountPolicy;
import com.airbnb.domain.policy.entity.FeePolicy;
import com.airbnb.domain.policy.repository.DiscountPolicyRepository;
import com.airbnb.domain.policy.repository.FeePolicyRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AmountCalculationService {

    private final FeePolicyRepository feePolicyRepository;
    private final DiscountPolicyRepository discountPolicyRepository;

    public AmountResult getAmountResult(Booking booking) {

        LocalDate checkIn = booking.getCheckIn();
        LocalDate checkOut = booking.getCheckOut();
        int cost = booking.getAccommodation().getCostPerNight();

        FeePolicy feePolicy = getAppliedFeePolicy(checkIn);
        DiscountPolicy discountPolicy = getAppliedDiscountPolicy(checkIn);

        int totalAmount = getTotalAmount(checkIn, checkOut, cost);
        int hostFeeAmount = getFeeAmount(totalAmount, feePolicy.getHostFeeRate());
        int guestFeeAmount = getFeeAmount(totalAmount, feePolicy.getGuestFeeRate());
        int discountAmount = getDiscountAmount(totalAmount, discountPolicy);
        int finalAmount = totalAmount + guestFeeAmount -  discountAmount;

        return AmountResult.builder()
            .totalAmount(totalAmount)
            .hostFeeAmount(hostFeeAmount)
            .guestFeeAmount(guestFeeAmount)
            .discountAmount(discountAmount)
            .finalAmount(finalAmount)
            .build();
    }

    private FeePolicy getAppliedFeePolicy(LocalDate baseDate) {
        return feePolicyRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(baseDate)
            .orElseThrow();
    }

    private DiscountPolicy getAppliedDiscountPolicy(LocalDate baseDate) {
        return discountPolicyRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(baseDate)
            .orElseThrow();
    }

    private int getTotalAmount(LocalDate checkIn, LocalDate checkOut, int cost) {
        int numberOfStays = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        return cost * numberOfStays;
    }

    private int getFeeAmount(int totalAmount, double feeRate) {
        return (int) (totalAmount * feeRate);
    }

    private int getDiscountAmount(int totalAmount, DiscountPolicy discountPolicy) {
        double totalDiscountRate = discountPolicy.getInitialDiscountRate() + discountPolicy.getWeeklyDiscountRate() + discountPolicy.getMonthlyDiscountRate();
        return (int) (totalAmount * totalDiscountRate);
    }
}