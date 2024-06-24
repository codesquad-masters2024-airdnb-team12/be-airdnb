package com.airbnb.domain.payment.controller;

import com.airbnb.domain.payment.dto.response.PaymentListResponse;
import com.airbnb.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/host/payments")
@RestController
@RequiredArgsConstructor
public class HostPaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<PaymentListResponse> getAllByRecipientAndStatus(@RequestParam(required = false) String status) {
        Long hostId = 1L;
        return ResponseEntity.ok(
                paymentService.getAllByHostIdAndStatus(hostId, status));
    }
}
