package com.airbnb.domain.booking.controller;

import com.airbnb.domain.booking.dto.response.BookingListResponse;
import com.airbnb.domain.booking.dto.response.BookingResponse;
import com.airbnb.domain.booking.service.HostBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/host/bookings")
@RestController
@RequiredArgsConstructor
public class HostBookingController {

    private final HostBookingService bookingService;

    // 예약 목록 조회
    @GetMapping
    public ResponseEntity<BookingListResponse> getAllByHostAndStatus(@RequestParam(required = false) String status) {
        Long hostId = 1L;

        return ResponseEntity.ok(bookingService.getAllByHostIdAndStatus(hostId, status));
    }

    // 예약 승인
    @PostMapping("/{bookingId}/approve")
    public ResponseEntity<BookingResponse> approve(@PathVariable Long bookingId) {
        Long hostId = 1L;

        return ResponseEntity.ok(bookingService.approve(hostId, bookingId));
    }

    // 예약 거절
    @PostMapping("/{bookingId}/reject")
    public ResponseEntity<BookingResponse> reject(@PathVariable Long bookingId) {
        Long hostId = 1L;

        return ResponseEntity.ok(bookingService.reject(hostId, bookingId));
    }
}
