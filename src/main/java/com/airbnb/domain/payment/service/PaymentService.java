package com.airbnb.domain.payment.service;

import com.airbnb.domain.payment.dto.response.PaymentListResponse;
import com.airbnb.domain.payment.dto.response.PaymentResponse;
import com.airbnb.domain.payment.entity.Payment;
import com.airbnb.domain.payment.entity.PaymentStatus;
import com.airbnb.domain.payment.repository.PaymentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentListResponse getAllByPayerKeyAndStatus(String payerKey, PaymentStatus status) {
        List<Payment> payments = paymentRepository.findByBookingGuestEmailAndStatus(payerKey, status);

        return PaymentListResponse.from(payments);
    }

    public PaymentListResponse getAllByRecipientKeyAndStatus(String recipientKey, PaymentStatus status) {
        List<Payment> payments = paymentRepository.findByBookingAccommodationHostEmailAndStatus(recipientKey, status);

        return PaymentListResponse.from(payments);
    }

    public PaymentResponse getById(Long paymentId) {
        Payment targetPayment = paymentRepository.findById(paymentId).orElseThrow();

        if (!validatePayerAuth(targetPayment) && !validateRecipientAuth(targetPayment)) {
            throw new IllegalArgumentException("조회 권한이 없습니다.");
        }

        return PaymentResponse.from(targetPayment);
    }

    private boolean validateRecipientAuth(Payment payment) {
        return payment.isRecipient(getLoggedInMemberKey());
    }

    private boolean validatePayerAuth(Payment payment) {
        return payment.isPayer(getLoggedInMemberKey());
    }

    private String getLoggedInMemberKey() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}