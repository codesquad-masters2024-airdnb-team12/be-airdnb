package com.airbnb.domain.AccommodationInfo.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InfoType {
    PRIMARY("주요 편의 시설/서비스"),
    DISABLED_ACCESSIBILITY("장애인 접근 편의 관련"),
    INTERNET("인터넷"),
    ACTIVITY_LEISURE("액티비티 및 레저 활동"),
    CLEAN_SAFETY("청결 및 안전"),
    FOOD_DRINK("식음료 시설/서비스"),
    SERVICE_CONVENIENCE("서비스 및 편의 시설"),
    KID("아동용 시설/서비스"),
    ACCESSIBILITY("출입/접근 서비스"),
    TRANSPORTATION("이동 편의 시설/서비스"),
    ACCOMMODATION("숙소에서 이용 가능");

    private final String description;
}
