package com.airbnb.domain.accommodation.dto.response;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.common.Address;
import com.airbnb.domain.common.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AccommodationDetailResponse {

    private Long id;
    private Long hostId;
    private String hostName;
    private String hostImgUrl;
    private String name;
    private Address address;
    private Coordinate coordinate;
    private int bedroom;
    private int bed;
    private int bath;
    private int maxGuests;
    private String description;
    private String accommodationType;
    private String buildingType;
    private AccommodationFacilityListResponse facilities;
    private int remainDiscountCnt;
    private int costPerNight;
    private boolean initialDiscountApplied;
    private boolean weeklyDiscountApplied;
    private boolean monthlyDiscountApplied;

    public static AccommodationDetailResponse of(Accommodation accommodation) {
        return AccommodationDetailResponse.builder()
                .id(accommodation.getId())
                .hostId(accommodation.getHost().getId())
                .hostName(accommodation.getHost().getName())
                .hostImgUrl(accommodation.getHost().getImgUrl())
                .name(accommodation.getName())
                .address(accommodation.getAddress())
                .coordinate(Coordinate.of(accommodation.getCoordinate()))
                .bedroom(accommodation.getBedroom())
                .bed(accommodation.getBed())
                .bath(accommodation.getBath())
                .maxGuests(accommodation.getMaxGuests())
                .description(accommodation.getDescription())
                .accommodationType(accommodation.getAccommodationType().getName())
                .buildingType(accommodation.getBuildingType().getName())
                .facilities(AccommodationFacilityListResponse.of(accommodation.getAccommodationFacilities().stream().toList()))
                .remainDiscountCnt(accommodation.getAccommodationDiscount().getRemainDiscountCnt())
                .costPerNight(accommodation.getCostPerNight())
                .initialDiscountApplied(accommodation.isInitialDiscountApplied())
                .weeklyDiscountApplied(accommodation.isWeeklyDiscountApplied())
                .monthlyDiscountApplied(accommodation.isMonthlyDiscountApplied())
                .build();
    }
}
