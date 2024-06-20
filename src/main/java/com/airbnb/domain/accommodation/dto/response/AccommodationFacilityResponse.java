package com.airbnb.domain.accommodation.dto.response;

import com.airbnb.domain.accommodationFacility.entity.AccommodationFacility;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccommodationFacilityResponse {

    private Long accommodationId;
    private Long facilityId;
    private String name;
    private String description;
    private Boolean forSearch;

    public static AccommodationFacilityResponse of(AccommodationFacility accommodationFacility) {
        return new AccommodationFacilityResponse(
                accommodationFacility.getAccommodation().getId(),
                accommodationFacility.getFacility().getId(),
                accommodationFacility.getFacility().getName(),
                accommodationFacility.getDescription(),
                accommodationFacility.getFacility().getForSearch());
    }
}
