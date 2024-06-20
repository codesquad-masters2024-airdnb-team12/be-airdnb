package com.airbnb.domain.facility.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FacilityResponse {

    private Long id;
    private String name;
    private Boolean forSearch;
}
