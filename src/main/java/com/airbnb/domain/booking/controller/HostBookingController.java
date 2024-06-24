package com.airbnb.domain.booking.controller;

import com.airbnb.domain.booking.dto.response.BookingListResponse;
import com.airbnb.domain.booking.dto.response.BookingResponse;
import com.airbnb.domain.booking.service.BookingService;
import com.airbnb.domain.common.BookingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/host/bookings")
@RestController
@RequiredArgsConstructor
public class HostBookingController {

    private final BookingService bookingService;

    /**
     * TODO:
     * 호스트 시점에서 볼 수 있는 예약 화면
     * - 예약 목록 조회
     * - 예약 상세 조회
     * - 예약 상태 변경 (승인, 취소, 거절)
     * => 이용중, 이용 완료는 자동변경되어야 함
     * -> 예약 자동 승인 기능 구현 (스케줄러)
     */

    @GetMapping
    public ResponseEntity<BookingListResponse> getAllByHostAndStatus(@RequestParam String status) {
        Long hostId = 1L;

        return ResponseEntity.ok(bookingService.getAllByHostIdAndStatus(hostId, status));
    }

    @PostMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> changeStatus(
            @PathVariable Long bookingId, @RequestParam String status
    ) {
        return ResponseEntity.ok(bookingService.changeStatus(bookingId, status));
    }
}
