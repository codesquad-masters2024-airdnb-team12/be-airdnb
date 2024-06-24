package com.airbnb.domain.booking.service;

import com.airbnb.domain.booking.dto.response.BookingListResponse;
import com.airbnb.domain.booking.dto.response.BookingResponse;
import com.airbnb.domain.booking.entity.Booking;
import com.airbnb.domain.booking.repository.BookingRepository;
import com.airbnb.domain.common.BookingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.airbnb.domain.common.BookingStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HostBookingService {

    private final BookingRepository bookingRepository;

    @Transactional
    public BookingResponse approve(Long hostId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if(!booking.isHost(hostId)) {
            throw new IllegalArgumentException("예약 승인 권한이 없습니다.");
        }

        if (booking.getStatus() != REQUESTED) {
            throw new IllegalArgumentException("요청 상태인 예약만 승인할 수 있습니다.");
        }

        booking.approve();

        return BookingResponse.from(booking);
    }

    @Transactional
    public BookingResponse reject(Long hostId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow();
        if(!booking.isHost(hostId)) {
            throw new IllegalArgumentException("예약 거절 권한이 없습니다.");
        }

        if (booking.getStatus() != REQUESTED) {
            throw new IllegalArgumentException("요청 상태인 예약만 거절할 수 있습니다.");
        }

        booking.reject();

        return BookingResponse.from(booking);
    }

    @Transactional
    public void updateStatusByCheckIn() {
        bookingRepository.findByCheckInEqualsAndStatus(LocalDate.now(), CONFIRMED)
                .forEach(booking -> booking.changeStatus(USING));
    }

    @Transactional
    public void updateStatusByCheckOut() {
        bookingRepository.findByCheckOutEqualsAndStatus(LocalDate.now(), USING)
                .forEach(booking -> booking.changeStatus(COMPLETED));
    }

    @Transactional
    public void approveBooking() {
        bookingRepository.findBookingsToApprove(LocalDateTime.now().minusHours(24))
                .forEach(booking -> booking.changeStatus(CONFIRMED));
    }

    public BookingListResponse getAllByHostIdAndStatus(Long hostId, String status) {
        List<Booking> bookings;

        if (status == null || status.isEmpty()) {
            bookings = bookingRepository.findByAccommodationHostId(hostId);
        } else {
            bookings = bookingRepository.findByAccommodationHostIdAndStatus(hostId, BookingStatus.from(status));
        }

        return BookingListResponse.from(bookings);
    }
}
