package com.airbnb.domain.booking.controller;

import com.airbnb.domain.booking.dto.request.BookingCreateRequest;
import com.airbnb.domain.booking.dto.response.BookingListResponse;
import com.airbnb.domain.booking.dto.response.BookingResponse;
import com.airbnb.domain.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/bookings")
@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/{accommodationId}")
    public ResponseEntity<BookingResponse> create(@PathVariable Long accommodationId, @Valid @RequestBody BookingCreateRequest request) {
        Long guestId = 2L;

        return ResponseEntity.ok(
                bookingService.create(guestId, accommodationId, request)
        );
    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingResponse> cancel(@PathVariable Long bookingId) {
        Long guestId = 1L;

        return ResponseEntity.ok(bookingService.cancel(guestId, bookingId));
    }

    @GetMapping
    public ResponseEntity<BookingListResponse> getAllByGuestAndStatus(@RequestParam(required = false) String status) {
        Long guestId = 1L;
        BookingListResponse bookings = bookingService.getAllByGuestIdAndStatus(guestId, status);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getById(@PathVariable Long bookingId) {
        Long guestId = 1L;
        return ResponseEntity.ok(bookingService.getById(guestId, bookingId));
    }
}