package com.airbnb.domain.booking.dto.response;

import com.airbnb.domain.booking.entity.Booking;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookingListResponse {

    private List<BookingResponse> bookings;

    public static BookingListResponse from(List<Booking> bookings) {
        return BookingListResponse.builder()
            .bookings(bookings.stream().map(BookingResponse::from).toList())
            .build();
    }
}