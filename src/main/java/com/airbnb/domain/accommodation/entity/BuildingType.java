package com.airbnb.domain.accommodation.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BuildingType {
    ALL("공간 전체", "게스트가 숙소 전체를 단독으로 사용합니다."),
    ROOM("방", "단독으로 사용하는 개인실이 있고, 공용 공간도 있는 형태입니다."),
    SHARED_ROOM("다인실", "게스트가 개인 공간 없이 호스트나 다른 사람과 함께 쓰는 침실이나 공용 공간에서 숙박합니다.");

    private final String name;
    private final String description;
}
