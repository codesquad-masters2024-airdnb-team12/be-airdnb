package com.airbnb.domain.payment.controller;

import com.airbnb.domain.payment.dto.response.PaymentListResponse;
import com.airbnb.domain.payment.dto.response.PaymentResponse;
import com.airbnb.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/payments")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<PaymentListResponse> getAllByGuestIdAndStatus(@RequestParam(required = false) String status) {
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