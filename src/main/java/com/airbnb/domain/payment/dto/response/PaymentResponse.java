package com.airbnb.domain.payment.dto.response;

import com.airbnb.domain.payment.entity.Card;
import com.airbnb.domain.payment.entity.Payment;
import com.airbnb.domain.payment.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PaymentResponse {

    private Long id;
    private PaymentStatus status;
    private Card card;
    private int totalAmount;
    private int hostFeeAmount;
    private int guestFeeAmount;
    private int discountAmount;
    private int finalAmount;

    public static PaymentResponse from(Payment payment) {
        return PaymentResponse.builder()
            .id(payment.getId())
            .status(payment.getStatus())
            .card(payment.getCard())
            .totalAmount(payment.getTotalAmount())
            .hostFeeAmount(payment.getHostFeeAmount())
            .guestFeeAmount(payment.getGuestFeeAmount())
            .discountAmount(payment.getDiscountAmount())
            .finalAmount(payment.getFinalAmount())
            .build();
    }
}