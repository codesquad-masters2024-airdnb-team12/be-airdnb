package com.airbnb.domain.payment.service;

import static com.airbnb.domain.booking.entity.BookingStatus.CONFIRMED;

import com.airbnb.domain.payment.dto.request.PaymentExecuteRequest;
import com.airbnb.domain.payment.dto.response.PaymentListResponse;
import com.airbnb.domain.payment.dto.response.PaymentResponse;
import com.airbnb.domain.payment.entity.Card;
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

    @Transactional
    public PaymentResponse execute(Long paymentId, PaymentExecuteRequest executeRequest) {
        Payment targetPayment = paymentRepository.findById(paymentId).orElseThrow();
        targetPayment.execute(Card.valueOf(executeRequest.getCardName()));
        targetPayment.getBooking().changeStatus(CONFIRMED);

        return PaymentResponse.from(targetPayment);
    }

    public PaymentListResponse getAllByGuestIdAndStatus(Long guestId, PaymentStatus status) {
        List<Payment> payments = paymentRepository.findByBookingGuestIdAndStatus(guestId, status);

        return PaymentListResponse.from(payments);
    }

    public PaymentListResponse getAllByHostIdAndStatus(Long hostId, PaymentStatus status) {
        List<Payment> payments = paymentRepository.findByBookingAccommodationHostIdAndStatus(hostId, status);

        return PaymentListResponse.from(payments);
    }
}