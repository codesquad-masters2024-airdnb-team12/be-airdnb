package com.airbnb.domain.accommodationFacility.repository;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.accommodationFacility.entity.AccommodationFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccommodationFacilityRepository extends JpaRepository<AccommodationFacility, Long> {

    List<AccommodationFacility> findAllByAccommodation(Accommodation accommodation);
}
