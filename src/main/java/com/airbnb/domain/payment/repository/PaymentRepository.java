package com.airbnb.domain.payment.repository;

import com.airbnb.domain.payment.entity.Payment;
import com.airbnb.domain.common.PaymentStatus;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByBookingGuestId(Long guestId);
    List<Payment> findByBookingAccommodationHostId(Long hostId);
    List<Payment> findByBookingGuestIdAndStatus(Long guestId, PaymentStatus status);
    List<Payment> findByBookingAccommodationHostIdAndStatus(Long hostId, PaymentStatus status);

    @Query("SELECT p FROM Payment p " +
            "WHERE p.booking.accommodation.host.id = :hostId " +
            "AND p.status = 'COMPLETED' " +
            "AND (CASE WHEN :startDate IS NOT NULL THEN p.createdAt >= :startDate ELSE TRUE END) " +
            "AND (CASE WHEN :endDate IS NOT NULL THEN p.createdAt <= :endDate ELSE TRUE END) " +
            "AND (CASE WHEN :accommodationId IS NOT NULL THEN p.booking.accommodation.id = :accommodationId ELSE TRUE END)")
    List<Payment> findAllByCondition(@Param("hostId") Long hostId,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate,
                                     @Param("accommodationId") Long accommodationId);

}