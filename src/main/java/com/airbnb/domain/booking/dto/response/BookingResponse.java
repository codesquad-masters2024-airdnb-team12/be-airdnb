package com.airbnb.domain.booking.dto.response;

import com.airbnb.domain.booking.entity.Booking;
import com.airbnb.domain.common.Address;
import com.airbnb.domain.common.Coordinate;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookingResponse {

    private Long id;
    private Long hostId;
    private String hostName;
    private Long guestId;
    private String guestName;
    private Long accommodationId;
    private String accommodationName;
    private Address address;
    private Coordinate coordinate;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int adults;
    private int children;
    private int infants;
    private String bookingStatus;
    private String paymentStatus;
    private String card;
    private Integer totalAmount;
    private Integer feeAmount;
    private Integer discountAmount;
    private Integer finalAmount;

    public static BookingResponse from(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .hostId(booking.getAccommodation().getHost().getId())
                .hostName(booking.getAccommodation().getHost().getName())
                .guestId(booking.getGuest().getId())
                .guestName(booking.getGuest().getName())
                .accommodationId(booking.getAccommodation().getId())
                .accommodationName(booking.getAccommodation().getName())
                .address(booking.getAccommodation().getAddress())
                .coordinate(Coordinate.of(booking.getAccommodation().getCoordinate()))
                .checkIn(booking.getCheckIn())
                .checkOut(booking.getCheckOut())
                .adults(booking.getAdults())
                .children(booking.getChildren())
                .infants(booking.getInfants())
                .bookingStatus(booking.getStatus().getDescription())
                .paymentStatus(booking.getPayment().getStatus().getDescription())
                .card(booking.getPayment().getCard().name())
                .totalAmount(booking.getPayment().getTotalAmount())
                .feeAmount(booking.getPayment().getGuestFeeAmount())
                .discountAmount(booking.getPayment().getDiscountAmount())
                .finalAmount(booking.getPayment().getFinalAmount())
                .build();
    }
}