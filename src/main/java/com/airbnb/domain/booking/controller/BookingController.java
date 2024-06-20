package com.airbnb.domain.booking.controller;

import com.airbnb.domain.booking.dto.request.BookingCreateRequest;
import com.airbnb.domain.booking.dto.response.BookingListResponse;
import com.airbnb.domain.booking.dto.response.BookingResponse;
import com.airbnb.domain.booking.entity.BookingStatus;
import com.airbnb.domain.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/bookings/{accommodationId}")
    public ResponseEntity<BookingResponse> create(@PathVariable Long accommodationId, @Valid @RequestBody BookingCreateRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String guestKey = authentication.getName();

        // 호스트가 자신의 숙소를 예약할 수 있는가?

        BookingResponse createdBooking = bookingService.create(guestKey, accommodationId, request);
        return ResponseEntity.ok(createdBooking);
    }

    @GetMapping("/bookings/{status}")
    public ResponseEntity<BookingListResponse> getAllByGuestAndStatus(@PathVariable String status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String guestKey = authentication.getName();
        BookingListResponse bookings = bookingService.getAllByGuestKeyAndStatus(guestKey, BookingStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/host/bookings/{status}")
    public ResponseEntity<BookingListResponse> getAllByHostAndStatus(@PathVariable String status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String hostKey = authentication.getName();
        BookingListResponse bookings = bookingService.getAllByHostKeyAndStatus(hostKey, BookingStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<BookingResponse> getById(@PathVariable Long bookingId) {
        BookingResponse targetBooking = bookingService.getById(bookingId);
        return ResponseEntity.ok(targetBooking);
    }

    @PostMapping("/bookings/{id}/cancel")
    public ResponseEntity<BookingResponse> cancel(@PathVariable Long bookingId) {
        BookingResponse canceledBooking = bookingService.cancel(bookingId);
        return ResponseEntity.ok(canceledBooking);
    }

    @PostMapping("/host/bookings/{id}/approve")
    public ResponseEntity<BookingResponse> approve(@PathVariable Long bookingId) {
        BookingResponse approvedBooking = bookingService.approve(bookingId);
        return ResponseEntity.ok(approvedBooking);
    }

    @PostMapping("/host/bookings/{id}/reject")
    public ResponseEntity<BookingResponse> reject(@PathVariable Long bookingId) {
        BookingResponse rejectedBooking = bookingService.reject(bookingId);
        return ResponseEntity.ok(rejectedBooking);
    }
}