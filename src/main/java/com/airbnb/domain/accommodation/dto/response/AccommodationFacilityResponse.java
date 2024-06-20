package com.airbnb.domain.accommodation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccommodationFacilityResponse {

    private Long id;
    private String name;
    private String description;
    private Boolean forSearch;
}
