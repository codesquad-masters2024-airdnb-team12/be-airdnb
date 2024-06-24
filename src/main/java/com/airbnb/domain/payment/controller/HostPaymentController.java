package com.airbnb.domain.payment.controller;

import com.airbnb.domain.payment.dto.response.RevenueResponse;
import com.airbnb.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequestMapping("/host/payments")
@RestController
@RequiredArgsConstructor
public class HostPaymentController {

    private final PaymentService paymentService;

    // 수익 조회
    @GetMapping
    public ResponseEntity<RevenueResponse> getRevenue(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Long accommodationId) {
        Long hostId = 1L;

        return ResponseEntity.ok(
                paymentService.getRevenue(hostId, startDate, endDate, accommodationId)
        );
    }
}
