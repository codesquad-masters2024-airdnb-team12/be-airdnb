package com.airbnb.domain.accommodation.repository;

import com.airbnb.domain.accommodation.dto.response.AccommodationOverview;
import com.airbnb.domain.accommodation.entity.Accommodation;

import java.util.Optional;

public interface AccommodationCustomRepo {

    Optional<Accommodation> findDetailById(Long accommodationId);

    Optional<AccommodationOverview> findOverviewById(Long accommodationId);
}
