package com.airbnb.domain.payment.controller;

import com.airbnb.domain.payment.dto.response.PaymentListResponse;
import com.airbnb.domain.payment.dto.response.PaymentResponse;
import com.airbnb.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/payments")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{status}")
    public ResponseEntity<PaymentListResponse> getAllByGuestIdAndStatus(@PathVariable String status) {
        Long guestId = 1L;

        return ResponseEntity.ok(
                paymentService.getAllByGuestIdAndStatus(guestId, status));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getById(@PathVariable Long paymentId) {
        Long guestId = 1L;
        return ResponseEntity.ok(paymentService.getById(guestId, paymentId));
    }
}