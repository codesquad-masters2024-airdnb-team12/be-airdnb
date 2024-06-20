package com.airbnb.domain.booking.dto.response;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.booking.entity.Booking;
import com.airbnb.domain.booking.entity.BookingStatus;
import com.airbnb.domain.member.entity.Member;
import com.airbnb.domain.payment.entity.Payment;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookingResponse {

    private Long id;
    private Member guest;
    private Accommodation accommodation;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int adults;
    private int children;
    private int infants;
    private BookingStatus status;
    private Payment payment;

    public static BookingResponse from(Booking booking) {
        return BookingResponse.builder()
            .id(booking.getId())
            .guest(booking.getGuest())
            .accommodation(booking.getAccommodation())
            .checkIn(booking.getCheckIn())
            .checkOut(booking.getCheckOut())
            .adults(booking.getAdults())
            .children(booking.getChildren())
            .infants(booking.getInfants())
            .status(booking.getStatus())
            .payment(booking.getPayment())
            .build();
    }
}