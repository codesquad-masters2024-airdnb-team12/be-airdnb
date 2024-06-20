package com.airbnb.domain.accommodation.dto.response;

import com.airbnb.domain.accommodation.entity.Accommodation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AccommodationPageResponse {

    private Long totalElements;
    private Integer currentPage;
    private Integer totalPages;
    private List<AccommodationResponse> content;

    public static AccommodationPageResponse of(Page<Accommodation> accommodations) {
        return AccommodationPageResponse.builder()
                .totalElements(accommodations.getTotalElements())
                .currentPage(accommodations.getNumber() + 1)
                .totalPages(accommodations.getTotalPages())
                .content(accommodations.stream().map(AccommodationResponse::of).toList())
                .build();
    }
}