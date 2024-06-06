package com.airbnb.domain.hashtag;

public enum HashtagType {
    /**
     * 1. 건물 유형 - 주택, 아파트, 헛간, 보트, 통나무집, 캠핑카...
     * 2. 숙소 유형 - 방, 다인실, 건물 전체
     * 3. 편의시설 - 무선 인터넷, TV, 주방, 세탁기, 건물 내 무료 주차...
     * 4. 특징 - 캠핑장, 해변 바로 앞, 한옥, 섬, 멋진 수영장, 한적한 시골...
     */
    BUILDING_TYPE, ACCOMMODATION_TYPE, AMENITY, SPECIAL_FEATURE
}
