package com.airbnb.domain.booking.repository;

import com.airbnb.domain.booking.entity.Booking;
import com.airbnb.domain.common.BookingStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByAccommodationId(Long accommodationId);
    List<Booking> findByGuestId(Long guestId);
    List<Booking> findByGuestIdAndStatus(Long guestId, BookingStatus status);
    List<Booking> findByAccommodationHostId(Long hostId);
    List<Booking> findByAccommodationHostIdAndStatus(Long hostId, BookingStatus status);
    List<Booking> findByCheckInEqualsAndStatus(LocalDate targetDate, BookingStatus status);
    List<Booking> findByCheckOutEqualsAndStatus(LocalDate targetDate, BookingStatus status);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Booking b " +
            "WHERE b.accommodation.id = :accommodationId " +
            "AND b.checkIn < :checkOut " +
            "AND b.checkOut > :checkIn " +
            "AND (b.status != 'CANCELED' OR b.status != 'REJECTED')")
    boolean isOverlapBookingExists(Long accommodationId, LocalDate checkIn, LocalDate checkOut);

    @Query("SELECT b FROM Booking b WHERE b.status = 'REQUESTED' AND b.createdAt < :elapsedHours")
    List<Booking> findBookingsToApprove(@Param("elapsedHours") LocalDateTime elapsedHours);
}