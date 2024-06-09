package com.airbnb.domain.accommodation.dto.request;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.common.Address;
import com.airbnb.domain.member.Member;
import lombok.*;
import org.springframework.data.geo.Point;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AccommodationCreateRequest {

    private String name;
    private String country;
    private String zipcode;
    private String district;
    private String region;
    private String address;
    private String detailedAddress;
    private Double latitude;    // 위도
    private Double longitude;   // 경도
    private Integer bedroom;
    private Integer bed;
    private Integer bath;
    private Integer maxGuests;
    private String description;
    private Integer costPerNight;
    private Boolean initialDiscountApplied;
    private Boolean weeklyDiscountApplied;
    private Boolean monthlyDiscountApplied;
    private String buildingType;        // 건물 유형
    private String accommodationType;   // 숙소 유형
    private List<String> amenities;     // 편의시설

    public Accommodation toEntity(Member host) {
        Address accommoAddress = Address.builder()
                .country(country)
                .zipcode(zipcode)
                .district(district)
                .region(region)
                .address(address)
                .detailedAddress(detailedAddress)
                .build();

        return Accommodation.builder()
                .host(host)
                .name(name)
                .address(accommoAddress)
                .coordinate(new Point(latitude, longitude))
                .bedroom(bedroom)
                .bed(bed)
                .bath(bath)
                .maxGuests(maxGuests)
                .description(description)
                .costPerNight(costPerNight)
                .initialDiscountApplied(initialDiscountApplied)
                .weeklyDiscountApplied(weeklyDiscountApplied)
                .monthlyDiscountApplied(monthlyDiscountApplied)
                .build();
    }
}