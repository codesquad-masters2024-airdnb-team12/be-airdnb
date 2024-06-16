package com.airbnb.domain.payment.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    COMPLETED("결제 완료"), // 이후, booking status 예약 확정으로 변경
    PENDING("결제 대기"), // 예약 승인 후, 변경
    WITHDRAWN("결제 철회"), // 예약 거절/취소 후, 변경
    ;

    private final String description;
}
