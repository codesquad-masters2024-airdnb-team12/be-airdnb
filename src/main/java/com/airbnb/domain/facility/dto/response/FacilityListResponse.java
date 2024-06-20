package com.airbnb.domain.facility.dto.response;

import com.airbnb.domain.facility.entity.Facility;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class FacilityListResponse {

    private Map<String, List<FacilityResponse>> facilityTypes;

    public static FacilityListResponse of(List<Facility> facilities) {
        Map<String, List<FacilityResponse>> facilitiesByType = facilities.stream()
                .collect(Collectors.groupingBy(
                                f -> f.getType().getDescription(),
                                Collectors.mapping(f -> new FacilityResponse(f.getId(), f.getName(), f.getForSearch()),
                                        Collectors.toList())
                        )
                );

        return new FacilityListResponse(facilitiesByType);
    }
}
