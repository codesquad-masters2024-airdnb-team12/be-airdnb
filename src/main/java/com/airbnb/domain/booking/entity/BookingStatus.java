package com.airbnb.domain.booking.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookingStatus {
    REQUESTED("예약 요청"), // default
    APPROVED("예약 승인 - 결제 대기"),
    CANCELED("예약 취소"),
    REJECTED("예약 거절"),
    CONFIRMED("예약 확정 - 결제 완료"),
    USING("이용 중"),
    COMPLETED("이용 완료"),
    ;

    private final String description;

}
