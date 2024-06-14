package com.airbnb.domain.accommodation.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AccommodationType {
    HOUSE("주택(예: 펜션, 한옥 등)"),
    APARTMENT("아파트"),
    BARN("헛간"),
    BNB("B&B"),
    BOAT("보트"),
    LOG_CABIN("통나무집"),
    CAMPING_CAR("캠핑카"),
    CASA_PARTICULAR("카사 파르티쿨라르(유니크한 민박집)"),
    CASTLE("성"),
    CAVE("동굴"),
    CONTAINER_HOUSE("컨테이너하우스"),
    HOTEL("호텔"),
    FARM("농장"),
    HOUSE_BOAT("하우스보트"),
    RYOKAN("료칸"),
    TENT("텐트"),
    TREE_HOUSE("트리하우스"),
    YURT("유르트"),
    OUTBUILDING_FOR_GUEST("게스트용 별채");

    private final String name;
}
