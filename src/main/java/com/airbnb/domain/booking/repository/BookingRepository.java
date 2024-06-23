package com.airbnb.domain.booking.repository;

import com.airbnb.domain.booking.entity.Booking;
import com.airbnb.domain.common.BookingStatus;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByAccommodationId(Long accommodationId);
    List<Booking> findByGuestEmailAndStatus(String guestEmail, BookingStatus status);
    List<Booking> findByAccommodationHostEmailAndStatus(String hostEmail, BookingStatus status);
    List<Booking> findByCheckInEqualsAndStatus(LocalDate targetDate, BookingStatus status);
    List<Booking> findByCheckOutEqualsAndStatus(LocalDate targetDate, BookingStatus status);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Booking b " +
            "WHERE b.accommodation.id = :accommodationId " +
            "AND b.checkIn < :checkOut " +
            "AND b.checkOut > :checkIn")
    boolean isOverlapBookingExists(Long accommodationId, LocalDate checkIn, LocalDate checkOut);
}