package com.airbnb.domain.payment.util;

import com.airbnb.domain.booking.entity.Booking;
import com.airbnb.domain.payment.dto.AmountResult;
import com.airbnb.domain.policy.entity.DiscountPolicy;
import com.airbnb.domain.policy.entity.FeePolicy;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;

@Component
public class AmountCalculationUtil {

    public AmountResult getAmountResult(Booking booking, FeePolicy feePolicy, DiscountPolicy discountPolicy) {

        LocalDate checkIn = booking.getCheckIn();
        LocalDate checkOut = booking.getCheckOut();
        int cost = booking.getAccommodation().getCostPerNight();

        int originalAmount = getTotalAmount(checkIn, checkOut, cost);
        int hostFeeAmount = getFeeAmount(originalAmount, feePolicy.getHostFeeRate());
        int guestFeeAmount = getFeeAmount(originalAmount, feePolicy.getGuestFeeRate());
        int discountAmount = getDiscountAmount(originalAmount, discountPolicy);
        int finalAmount = originalAmount + guestFeeAmount - discountAmount;

        return AmountResult.builder()
            .totalAmount(originalAmount)
            .hostFeeAmount(hostFeeAmount)
            .guestFeeAmount(guestFeeAmount)
            .discountAmount(discountAmount)
            .finalAmount(finalAmount)
            .build();
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