package com.airbnb.domain.accommodation.dto.request;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.common.Address;
import com.airbnb.domain.member.entity.Member;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.geo.Point;

import java.util.List;

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
    private String district;

    @NotBlank
    private String region;

    @NotBlank
    private String address;
    private String detailedAddress;

    @Min(value = -90)
    @Max(value = 90)
    private double latitude;    // 위도

    @Min(value = -180)
    @Max(value = 180)
    private double longitude;   // 경도

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

    @Size(max = 1000)
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