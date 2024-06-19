package com.airbnb.domain.accommodation.dto.request;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.common.AccommodationType;
import com.airbnb.domain.common.BuildingType;
import com.airbnb.domain.common.Address;
import com.airbnb.domain.common.Coordinate;
import com.airbnb.global.util.GeometryUtil;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AccommodationOverviewEditRequest {

    @NotBlank
    @Size(max = 120)
    private String name;

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

    @NotBlank
    @Size(max = 5000)
    private String description;
    private Address address;
    private Coordinate coordinate;
    private String accommodationType;
    private String buildingType;

    public void updateEntity(Accommodation accommodation, boolean bookingExists) {
        // 위치, 숙소, 건물정보는 예약이 없고, 변경사항이 있을 때만 수정 가능
        accommodation.updateAccommodationOverview(name, bedroom, bed, bath, maxGuests, description,
                !bookingExists && address != null ? address : null,
                !bookingExists && coordinate != null ? GeometryUtil.createPoint(coordinate.getLongitude(), coordinate.getLatitude()) : null,
                !bookingExists && accommodationType != null && !accommodationType.isEmpty() ? AccommodationType.of(accommodationType) : null,
                !bookingExists && buildingType != null && !buildingType.isEmpty() ? BuildingType.of(buildingType) : null);
    }
}
