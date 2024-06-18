package com.airbnb.domain.accommodation.dto.request;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.accommodation.entity.AccommodationType;
import com.airbnb.domain.accommodation.entity.BuildingType;
import com.airbnb.domain.common.Address;
import com.airbnb.domain.common.Coordinate;
import com.airbnb.domain.member.entity.Member;
import com.airbnb.global.validator.NoNullElements;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AccommodationCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String country;

    @NotBlank
    private String zipcode;

    @NotBlank
    private String address;
    private String detailedAddress;

    @NotNull
    private Coordinate coordinate;

    @Min(1)
    @Max(20)
    private int bedroom;

    @Min(0)
    @Max(20)
    private int bed;

    @Min(0)
    @Max(20)
    private int bath;

    @Min(1)
    @Max(20)
    private int maxGuests;

    @Size(max = 5000)
    private String description;

    @Min(10_000)
    @Max(10_000_000)
    private int costPerNight;
    private boolean initialDiscountApplied;
    private boolean weeklyDiscountApplied;
    private boolean monthlyDiscountApplied;

    @NotBlank
    private String buildingType;        // 건물 유형

    @NotBlank
    private String accommodationType;   // 숙소 유형

    @NotNull
    @NoNullElements
    private Set<String> facilities;     // 필수 편의시설

    @NotNull
    private Set<AccommodationFacilityInfoRequest> info; // 호스트 입력 숙소 편의시설 정보

    public Accommodation toEntity(Member host) {
        Address accommoAddress = Address.builder()
                .country(country)
                .zipcode(zipcode)
                .address(address)
                .detailedAddress(detailedAddress)
                .build();

        return Accommodation.builder()
                .host(host)
                .name(name)
                .address(accommoAddress)
                .longitude(coordinate.getLongitude())
                .latitude(coordinate.getLatitude())
                .bedroom(bedroom)
                .bed(bed)
                .bath(bath)
                .maxGuests(maxGuests)
                .accommodationType(AccommodationType.valueOf(accommodationType))
                .buildingType(BuildingType.valueOf(buildingType))
                .description(description)
                .costPerNight(costPerNight)
                .initialDiscountApplied(initialDiscountApplied)
                .weeklyDiscountApplied(weeklyDiscountApplied)
                .monthlyDiscountApplied(monthlyDiscountApplied)
                .build();
    }
}