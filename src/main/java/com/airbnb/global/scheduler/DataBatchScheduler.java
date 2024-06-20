package com.airbnb.global.scheduler;

import com.airbnb.domain.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataBatchScheduler {

    private final BookingService bookingService;

    @Scheduled(cron = "0 0 15 * * ?")
    public void updateBookingStatusByCheckIn() {
        bookingService.updateStatusByCheckIn();
    }

    @Scheduled(cron = "0 0 11 * * ?")
    public void updateBookingStatusByCheckOut() {
        bookingService.updateStatusByCheckOut();
    }
}
