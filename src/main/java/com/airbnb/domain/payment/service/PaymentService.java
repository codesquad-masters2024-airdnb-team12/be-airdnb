package com.airbnb.domain.payment.service;

import com.airbnb.domain.payment.dto.response.PaymentListResponse;
import com.airbnb.domain.payment.dto.response.PaymentResponse;
import com.airbnb.domain.payment.entity.Payment;
import com.airbnb.domain.common.PaymentStatus;
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

    public PaymentListResponse getAllByGuestIdAndStatus(Long guestId, String status) {
        List<Payment> payments;

        if(status == null || status.isEmpty()) {
            payments = paymentRepository.findByBookingGuestId(guestId);
        } else {
            payments = paymentRepository.findByBookingGuestIdAndStatus(guestId, PaymentStatus.from(status));
        }

        return PaymentListResponse.from(payments);
    }

    public PaymentListResponse getAllByHostIdAndStatus(Long hostId, String status) {
        List<Payment> payments;

        if(status == null || status.isEmpty()) {
            payments = paymentRepository.findByBookingAccommodationHostId(hostId);
        } else {
            payments = paymentRepository.findByBookingAccommodationHostIdAndStatus(hostId, PaymentStatus.from(status));
        }

        return PaymentListResponse.from(payments);
    }

    public PaymentResponse getById(Long guestId, Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();

        if (!payment.isPayer(guestId)) {
            throw new IllegalArgumentException("조회 권한이 없습니다.");
        }

        return PaymentResponse.from(payment);
    }

}