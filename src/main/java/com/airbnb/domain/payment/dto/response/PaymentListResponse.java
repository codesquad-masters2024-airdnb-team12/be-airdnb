package com.airbnb.domain.payment.dto.response;

import com.airbnb.domain.payment.entity.Payment;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PaymentListResponse {

    private List<PaymentResponse> payments;
    private long numberOfPayments;
    private long sumOfTotalAmount;
    private long sumOfHostFeeAmount;
    private long sumOfGuestFeeAmount;
    private long sumOfDiscountAmount;
    private long sumOfFinalAmount;

    public static PaymentListResponse from(List<Payment> payments) {
        return PaymentListResponse.builder()
            .payments(payments.stream().map(PaymentResponse::from).toList())
            .numberOfPayments(payments.size())
            .sumOfTotalAmount(payments.stream().mapToInt(Payment::getTotalAmount).sum())
            .sumOfTotalAmount(payments.stream().mapToInt(Payment::getHostFeeAmount).sum())
            .sumOfTotalAmount(payments.stream().mapToInt(Payment::getGuestFeeAmount).sum())
            .sumOfTotalAmount(payments.stream().mapToInt(Payment::getDiscountAmount).sum())
            .sumOfTotalAmount(payments.stream().mapToInt(Payment::getFinalAmount).sum())
            .build();
    }
}
