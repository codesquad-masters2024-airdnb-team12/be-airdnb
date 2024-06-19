package com.airbnb.domain.accommodation.dto.response;

import com.airbnb.domain.common.FacilityType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
public class AccommodationFacilities {

    private Map<String, Set<String>> facilities;

    @JsonIgnore
    private Long hostId;

    @JsonIgnore
    private Set<FacilityInfo> facilitySet;

    @JsonIgnore
    private Set<CustomizedFacilityInfo> customizedFacilitySet;

    public AccommodationFacilities(Long hostId, Set<FacilityInfo> facilitySet, Set<CustomizedFacilityInfo> customizedFacilitySet) {
        this.hostId = hostId;
        this.facilitySet = facilitySet;
        this.customizedFacilitySet = customizedFacilitySet;
    }

    public void setFacilities(Map<String, Set<String>> facilities) {
        this.facilities = facilities;
    }

    @Getter
    @AllArgsConstructor
    public static class FacilityInfo {
        private String name;
        private FacilityType type;
    }

    @Getter
    @AllArgsConstructor
    public static class CustomizedFacilityInfo {
        private String name;
        private FacilityType type;
    }
}
