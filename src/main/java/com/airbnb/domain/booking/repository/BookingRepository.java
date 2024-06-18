package com.airbnb.domain.booking.repository;

import com.airbnb.domain.booking.entity.Booking;
import com.airbnb.domain.booking.entity.BookingStatus;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByGuestIdAndStatus(Long guestId, BookingStatus status);
    List<Booking> findByAccommodationHostIdAndStatus(Long hostId, BookingStatus status);
    List<Booking> findByCheckInEqualsAndStatus(LocalDate targetDate, BookingStatus status);
    List<Booking> findByCheckOutEqualsAndStatus(LocalDate targetDate, BookingStatus status);
}