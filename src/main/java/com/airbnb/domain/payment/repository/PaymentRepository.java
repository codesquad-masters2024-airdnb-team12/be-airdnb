package com.airbnb.domain.payment.repository;

import com.airbnb.domain.payment.entity.Payment;
import com.airbnb.domain.common.PaymentStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByBookingGuestIdAndStatus(Long guestId, PaymentStatus status);
    List<Payment> findByBookingAccommodationHostIdAndStatus(Long hostId, PaymentStatus status);
}