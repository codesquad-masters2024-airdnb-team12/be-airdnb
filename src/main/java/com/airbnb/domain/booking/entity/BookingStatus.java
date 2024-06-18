package com.airbnb.domain.booking.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookingStatus {
    REQUESTED("예약 요청"), // default
    CANCELED("예약 취소"),
    REJECTED("예약 거절"),
    CONFIRMED("예약 확정"),
    USING("이용 중"),
    COMPLETED("이용 완료"),
    ;

    private final String description;

}
