package com.airbnb.domain.accommodation.dto.response;

import com.airbnb.domain.accommodationFacility.entity.AccommodationFacility;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class AccommodationFacilityListResponse {

    private Map<String, List<AccommodationFacilityResponse>> facilityTypes;

    public static AccommodationFacilityListResponse of(List<AccommodationFacility> facilities) {
        Map<String, List<AccommodationFacilityResponse>> facilitiesByType = facilities.stream()
                .collect(Collectors.groupingBy(
                                f -> f.getFacility().getType().getDescription(),
                                Collectors.mapping(AccommodationFacilityResponse::of, Collectors.toList())
                        )
                );

        return new AccommodationFacilityListResponse(facilitiesByType);
    }
}
