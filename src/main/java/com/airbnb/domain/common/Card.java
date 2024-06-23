package com.airbnb.domain.common;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Card {
    SHARPIE, SYLVE;

    public static Card of(String card) {
        return Arrays.stream(Card.values())
                .filter(c -> c.name().equalsIgnoreCase(card))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당하는 카드 정보가 없습니다."));
    }
}