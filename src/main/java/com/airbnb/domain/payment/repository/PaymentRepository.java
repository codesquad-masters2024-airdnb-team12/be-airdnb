package com.airbnb.domain.payment.repository;

import com.airbnb.domain.payment.entity.Payment;
import com.airbnb.domain.payment.entity.PaymentStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByBookingGuestEmailAndStatus(String payerEmail, PaymentStatus status);
    List<Payment> findByBookingAccommodationHostEmailAndStatus(String recipientEmail, PaymentStatus status);
}