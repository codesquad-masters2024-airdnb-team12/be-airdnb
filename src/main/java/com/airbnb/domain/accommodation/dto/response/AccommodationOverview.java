package com.airbnb.domain.accommodation.dto.response;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.common.AccommodationType;
import com.airbnb.domain.common.BuildingType;
import com.airbnb.domain.common.Address;
import com.airbnb.domain.common.Coordinate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Point;

@Getter
@Builder
@AllArgsConstructor
public class AccommodationOverview {

    private Long id;

    @JsonIgnore
    private Long hostId;    // 자신이 등록한 숙소인지 확인하기 위함이지 반환용 x
    private String name;
    private int bedroom;
    private int bed;
    private int bath;
    private int maxGuests;
    private String description;

    // 아래 필드들은 이미 예약한 사용자가 있는 경우 수정 불가
    private Address address;
    private Coordinate coordinate;
    private String accommodationType;
    private String buildingType;

    @QueryProjection
    public AccommodationOverview(Long id, Long hostId, String name, int bedroom, int bed, int bath, int maxGuests, String description, Address address, Point coordinate, AccommodationType accommodationType, BuildingType buildingType) {
        this.id = id;
        this.hostId = hostId;
        this.name = name;
        this.bedroom = bedroom;
        this.bed = bed;
        this.bath = bath;
        this.maxGuests = maxGuests;
        this.description = description;
        this.address = address;
        this.coordinate = Coordinate.of(coordinate);
        this.accommodationType = accommodationType.getName();
        this.buildingType = buildingType.getName();
    }

    public static AccommodationOverview of(Accommodation accommodation) {
        return AccommodationOverview.builder()
                .id(accommodation.getId())
                .name(accommodation.getName())
                .bedroom(accommodation.getBedroom())
                .bed(accommodation.getBed())
                .bath(accommodation.getBath())
                .maxGuests(accommodation.getMaxGuests())
                .description(accommodation.getDescription())
                .address(accommodation.getAddress())
                .coordinate(Coordinate.of(accommodation.getCoordinate()))
                .accommodationType(accommodation.getAccommodationType().getName())
                .buildingType(accommodation.getBuildingType().getName())
                .build();
    }
}
