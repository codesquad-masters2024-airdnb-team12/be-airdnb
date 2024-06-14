package com.airbnb.domain.accommodation.dto.response;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.common.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.geo.Point;

@Getter
@Builder
@AllArgsConstructor
public class AccommodationResponse {

    private Long id;
    private String name;
    private Address address;
    private Point coordinate;
    private int bedroom;
    private int bed;
    private int bath;
    private int maxGuests;
    private int costPerNight;

    public static AccommodationResponse from(Accommodation accommodation) {
        return AccommodationResponse.builder()
                .id(accommodation.getId())
                .name(accommodation.getName())
                .address(accommodation.getAddress())
                .coordinate(accommodation.getCoordinate())
                .bedroom(accommodation.getBedroom())
                .bed(accommodation.getBed())
                .bath(accommodation.getBath())
                .maxGuests(accommodation.getMaxGuests())
                .costPerNight(accommodation.getCostPerNight())
                .build();
    }
}