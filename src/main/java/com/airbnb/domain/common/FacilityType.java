package com.airbnb.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum FacilityType {
    DISABLED_ACCESSIBILITY("장애인 접근 편의 관련"),
    INTERNET_ELECTRONICS("인터넷 및 전자기기"),
    ACTIVITY_LEISURE("액티비티 및 레저 활동"),
    CLEAN_SAFETY("청결 및 안전"),
    FOOD_DRINK("식음료 시설/서비스"),
    SERVICE_CONVENIENCE("서비스 및 편의 시설"),
    KID("아동용 시설/서비스"),
    ACCESSIBILITY("출입/접근 서비스"),
    TRANSPORTATION("이동 편의 시설/서비스"),
    ACCOMMODATION("숙소에서 이용 가능");

    private final String description;

    public static FacilityType of(String facilityType) {
        return Arrays.stream(FacilityType.values())
                .filter(f -> f.name().equalsIgnoreCase(facilityType) || f.getDescription().equals(facilityType))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 편의시설 타입입니다."));
    }
}
