package com.airbnb.domain.accommodationFacility;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class AccommodationFacilityId implements Serializable {

    @Column(nullable = false)
    private Long accommodationId;

    @Column(nullable = false)
    private Long facilityId;

    public AccommodationFacilityId(Long accommodationId, Long facilityId) {
        this.accommodationId = accommodationId;
        this.facilityId = facilityId;
    }
}
