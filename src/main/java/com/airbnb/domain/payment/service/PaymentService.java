package com.airbnb.domain.payment.service;

import com.airbnb.domain.payment.dto.response.PaymentListResponse;
import com.airbnb.domain.payment.entity.Payment;
import com.airbnb.domain.payment.entity.PaymentStatus;
import com.airbnb.domain.payment.repository.PaymentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentListResponse getAllByGuestIdAndStatus(Long guestId, PaymentStatus status) {
        List<Payment> payments = paymentRepository.findByBookingGuestIdAndStatus(guestId, status);

        return PaymentListResponse.from(payments);
    }

    public PaymentListResponse getAllByHostIdAndStatus(Long hostId, PaymentStatus status) {
        List<Payment> payments = paymentRepository.findByBookingAccommodationHostIdAndStatus(hostId, status);

        return PaymentListResponse.from(payments);
    }
}