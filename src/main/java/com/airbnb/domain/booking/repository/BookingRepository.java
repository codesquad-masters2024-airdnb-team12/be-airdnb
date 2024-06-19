package com.airbnb.domain.booking.repository;

import com.airbnb.domain.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByAccommodationId(Long accommodationId);
}
