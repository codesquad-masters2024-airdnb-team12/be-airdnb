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

    public static PaymentListResponse from(List<Payment> payments) {
        return PaymentListResponse.builder()
            .payments(payments.stream().map(PaymentResponse::of).toList())
            .build();
    }
}
