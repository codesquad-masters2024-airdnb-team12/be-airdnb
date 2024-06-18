package com.airbnb.domain.accommodation.dto.response;

import com.airbnb.domain.accommodation.entity.Accommodation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class AccommodationPageResponse {

    private Page<AccommodationResponse> accommodationResponses;

    public static AccommodationPageResponse of(Page<Accommodation> accommodationPage) {
        return AccommodationPageResponse.builder()
                .accommodationResponses(
                        new PageImpl<>(
                                accommodationPage.getContent().stream().map(AccommodationResponse::of).collect(Collectors.toList()),
                                accommodationPage.getPageable(), accommodationPage.getTotalPages())
                )
                .build();
    }
}