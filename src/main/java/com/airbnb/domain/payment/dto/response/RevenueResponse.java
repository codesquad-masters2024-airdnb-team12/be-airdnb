package com.airbnb.domain.payment.dto.response;

import com.airbnb.domain.payment.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RevenueResponse {

    private BigDecimal totalRevenue;
    private BigDecimal totalHostFeeAmount;
    private List<PaymentResponse> payments;

    public static RevenueResponse of(BigDecimal totalRevenue, BigDecimal totalHostFeeAmount, List<Payment> payments) {
        return RevenueResponse.builder()
                .totalRevenue(totalRevenue)
                .payments(payments.stream().map(PaymentResponse::of).toList())
                .totalRevenue(totalRevenue)
                .totalHostFeeAmount(totalHostFeeAmount)
                .build();
    }
}
