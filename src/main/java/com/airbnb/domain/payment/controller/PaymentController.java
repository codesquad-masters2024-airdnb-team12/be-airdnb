package com.airbnb.domain.payment.controller;

import com.airbnb.domain.payment.dto.response.PaymentListResponse;
import com.airbnb.domain.payment.dto.response.PaymentResponse;
import com.airbnb.domain.payment.entity.PaymentStatus;
import com.airbnb.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/payments/{status}")
    public ResponseEntity<PaymentListResponse> getAllByPayerAndStatus(@PathVariable String status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String payerKey = authentication.getName();
        PaymentListResponse payments = paymentService.getAllByPayerKeyAndStatus(payerKey, PaymentStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/host/payments/{status}")
    public ResponseEntity<PaymentListResponse> getAllByRecipientAndStatus(@PathVariable String status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String recipientKey = authentication.getName();
        PaymentListResponse payments = paymentService.getAllByRecipientKeyAndStatus(recipientKey, PaymentStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/payments/{id}")
    public ResponseEntity<PaymentResponse> getById(@PathVariable Long paymentId) {
        PaymentResponse targetPayment = paymentService.getById(paymentId);
        return ResponseEntity.ok(targetPayment);
    }
}