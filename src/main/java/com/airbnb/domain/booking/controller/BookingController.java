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

    /**
     * TODO:
     * 게스트 시점에서 볼 수 있는 예약 화면
     * - 예약 하기
     * - 예약 상태 변경(취소)
     * - 예약 정보 수정
     * - 예약 조회
     * - 예약 목록 조회
     */

    // 예약하기
    @PostMapping("/{accommodationId}")
    public ResponseEntity<BookingResponse> create(@PathVariable Long accommodationId, @Valid @RequestBody BookingCreateRequest request) {
        Long guestId = 1L;

        // TODO: 호스트는 자신의 숙소 예약 불가
        return ResponseEntity.ok(
                bookingService.create(guestId, accommodationId, request)
        );
    }

    @GetMapping
    public ResponseEntity<BookingListResponse> getAllByGuestAndStatus(@RequestParam String status) {
        Long guestId = 1L;
        BookingListResponse bookings = bookingService.getAllByGuestIdAndStatus(guestId, status);
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getById(@PathVariable Long bookingId) {
        BookingResponse targetBooking = bookingService.getById(bookingId);
        return ResponseEntity.ok(targetBooking);
    }

    @PostMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> changeStatus(
            @PathVariable Long bookingId, @RequestParam("status") String status) {
        return ResponseEntity.ok(bookingService.changeStatus(bookingId, status));
    }
}