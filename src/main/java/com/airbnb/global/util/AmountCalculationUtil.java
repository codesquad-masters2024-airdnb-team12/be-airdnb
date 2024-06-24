package com.airbnb.global.util;

import com.airbnb.domain.booking.entity.Booking;
import com.airbnb.domain.common.AmountResult;
import com.airbnb.domain.policy.entity.DiscountPolicy;
import com.airbnb.domain.policy.entity.FeePolicy;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AmountCalculationUtil {

    public static AmountResult getAmountResult(Booking booking, FeePolicy feePolicy, DiscountPolicy discountPolicy) {

        LocalDate checkIn = booking.getCheckIn();
        LocalDate checkOut = booking.getCheckOut();
        int cost = booking.getAccommodation().getCostPerNight();

        int originalAmount = getTotalAmount(checkIn, checkOut, cost);
        int hostFeeAmount = getFeeAmount(originalAmount, feePolicy.getHostFeeRate());
        int guestFeeAmount = getFeeAmount(originalAmount, feePolicy.getGuestFeeRate());
        int discountAmount = getDiscountAmount(booking, originalAmount, discountPolicy);
        int finalAmount = originalAmount + guestFeeAmount - discountAmount;

        return AmountResult.builder()
                .feePolicy(feePolicy)
                .discountPolicy(discountPolicy)
                .totalAmount(originalAmount)
                .hostFeeAmount(hostFeeAmount)
                .guestFeeAmount(guestFeeAmount)
                .discountAmount(discountAmount)
                .finalAmount(finalAmount)
                .build();
    }

    private static int getTotalAmount(LocalDate checkIn, LocalDate checkOut, int cost) {
        int numberOfStays = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        return cost * numberOfStays;
    }

    private static int getFeeAmount(int totalAmount, double feeRate) {
        double fee = totalAmount * feeRate;
        return (int) fee;
    }

    private static int getDiscountAmount(Booking booking, int totalAmount, DiscountPolicy discountPolicy) {
        int remainDiscountCnt = booking.getAccommodation().getInitialDiscountCnt();
        int numberOfStays = (int) ChronoUnit.DAYS.between(booking.getCheckIn(), booking.getCheckOut());

        int months = numberOfStays / 28;
        int remainingDaysAfterMonths = numberOfStays % 28;
        int weeks = remainingDaysAfterMonths / 7;

        double initialDiscountRate = remainDiscountCnt > 0 ? discountPolicy.getInitialDiscountRate() : 0.0;
        double weeklyDiscountRate = weeks > 0 ? discountPolicy.getWeeklyDiscountRate() : 0.0;
        double monthlyDiscountRate = months > 0 ? discountPolicy.getMonthlyDiscountRate() : 0.0;

        double totalDiscountRate = initialDiscountRate + weeklyDiscountRate + monthlyDiscountRate;
        return (int) (totalAmount * totalDiscountRate);
    }
}