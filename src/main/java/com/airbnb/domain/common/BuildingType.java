package com.airbnb.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
@RequiredArgsConstructor
public enum BuildingType {
    ALL("공간 전체", "게스트가 숙소 전체를 단독으로 사용합니다."),
    ROOM("방", "단독으로 사용하는 개인실이 있고, 공용 공간도 있는 형태입니다."),
    SHARED_ROOM("다인실", "게스트가 개인 공간 없이 호스트나 다른 사람과 함께 쓰는 침실이나 공용 공간에서 숙박합니다.");

    private final String name;
    private final String description;

    // TODO: 예외 처리
    public static BuildingType of(String buildingType) {
        return Arrays.stream(BuildingType.values())
                .filter(b -> b.getName().equals(buildingType) || b.name().equalsIgnoreCase(buildingType))
                .findAny().orElseThrow(() -> new NoSuchElementException("해당 건물 유형이 존재하지 않습니다."));
    }
}
