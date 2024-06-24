package com.airbnb.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

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

    public static BookingStatus from(String status) {
        return Arrays.stream(BookingStatus.values())
                .filter(s -> s.name().equalsIgnoreCase(status))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("요청한 예약 상태가 존재하지 않습니다."));
    }
}
