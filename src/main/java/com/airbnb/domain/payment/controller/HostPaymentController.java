package com.airbnb.domain.payment.controller;

import com.airbnb.domain.payment.dto.response.PaymentListResponse;
import com.airbnb.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/host/payments")
@RestController
@RequiredArgsConstructor
public class HostPaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{status}")
    public ResponseEntity<PaymentListResponse> getAllByRecipientAndStatus(@PathVariable String status) {
        Long hostId = 1L;
        return ResponseEntity.ok(
                paymentService.getAllByHostIdAndStatus(hostId, status));
    }
}
