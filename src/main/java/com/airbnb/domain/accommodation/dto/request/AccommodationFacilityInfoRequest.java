package com.airbnb.domain.accommodation.dto.request;

import com.airbnb.domain.AccommodationCustomizedFacility.entity.AccommodationCustomizedFacility;
import com.airbnb.domain.common.FacilityType;
import com.airbnb.domain.accommodation.entity.Accommodation;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
public class AccommodationFacilityInfoRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String type;

    public AccommodationCustomizedFacility toEntity(Accommodation accommodation) {
        return AccommodationCustomizedFacility.builder()
                .accommodation(accommodation)
                .name(name)
                .type(FacilityType.valueOf(type))
                .build();
    }
}
