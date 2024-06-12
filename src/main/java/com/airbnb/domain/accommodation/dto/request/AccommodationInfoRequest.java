package com.airbnb.domain.accommodation.dto.request;

import com.airbnb.domain.AccommodationInfo.entity.AccommodationInfo;
import com.airbnb.domain.AccommodationInfo.entity.InfoType;
import com.airbnb.domain.accommodation.entity.Accommodation;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode
public class AccommodationInfoRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String type;

    public AccommodationInfo toEntity(Accommodation accommodation) {
        return AccommodationInfo.builder()
                .accommodation(accommodation)
                .name(name)
                .type(InfoType.valueOf(type))
                .build();
    }
}
