package com.airbnb.domain.booking.controller;

import com.airbnb.domain.booking.dto.request.BookingCreateRequest;
import com.airbnb.domain.booking.dto.response.BookingListResponse;
import com.airbnb.domain.booking.dto.response.BookingResponse;
import com.airbnb.domain.common.BookingStatus;
import com.airbnb.domain.booking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/bookings/{accommodationId}")
    public ResponseEntity<BookingResponse> create(@PathVariable Long accommodationId, @Valid @RequestBody BookingCreateRequest request) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String guestKey = authentication.getName();
        String guestKey = "parkeh@example.com";

        // 호스트가 자신의 숙소를 예약할 수 있는가? => 불가

        BookingResponse createdBooking = bookingService.create(guestKey, accommodationId, request);
        return ResponseEntity.ok(createdBooking);
    }

    @GetMapping("/bookings")
    public ResponseEntity<BookingListResponse> getAllByGuestAndStatus(@RequestParam String status) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String guestKey = authentication.getName();

        String guestKey = "parkeh@example.com";
        BookingListResponse bookings = bookingService.getAllByGuestKeyAndStatus(guestKey, BookingStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/host/bookings")
    public ResponseEntity<BookingListResponse> getAllByHostAndStatus(@RequestParam String status) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String hostKey = authentication.getName();

        String hostKey = "parkeh@example.com";
        BookingListResponse bookings = bookingService.getAllByHostKeyAndStatus(hostKey, BookingStatus.valueOf(status.toUpperCase()));
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<BookingResponse> getById(@PathVariable Long bookingId) {
        BookingResponse targetBooking = bookingService.getById(bookingId);
        return ResponseEntity.ok(targetBooking);
    }

    @PostMapping("/bookings/{bookingId}/changeStatus")
    public ResponseEntity<BookingResponse> changeStatus(
            @PathVariable Long bookingId, @RequestParam("status") String status) {
        return ResponseEntity.ok(bookingService.changeStatus(bookingId, status));
    }
}