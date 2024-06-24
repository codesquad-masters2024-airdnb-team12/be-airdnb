package com.airbnb.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    COMPLETED("결제 완료"), // 이후, booking status 예약 확정으로 변경
    PENDING("결제 대기"), // 예약 승인 후, 변경
    WITHDRAWN("결제 철회"), // 예약 거절/취소 후, 변경
    ;

    private final String description;

    public static PaymentStatus from(String paymentStatus) {
        return Arrays.stream(PaymentStatus.values())
                .filter(ps -> ps.getDescription().equalsIgnoreCase(paymentStatus))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("해당 결제 상태가 존재하지 않습니다."));
    }
}
