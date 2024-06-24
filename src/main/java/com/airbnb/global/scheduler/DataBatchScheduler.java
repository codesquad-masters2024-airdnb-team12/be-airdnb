package com.airbnb.global.scheduler;

import com.airbnb.domain.booking.service.HostBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataBatchScheduler {

    private final HostBookingService bookingService;

    @Scheduled(cron = "0 0 15 * * ?")
    public void updateBookingStatusByCheckIn() {
        bookingService.updateStatusByCheckIn();
    }

    @Scheduled(cron = "0 0 11 * * ?")
    public void updateBookingStatusByCheckOut() {
        bookingService.updateStatusByCheckOut();
    }

    // 1시간마다 실행되도록 구현
    @Scheduled(cron = "0 0 * * * ?")
    public void approveBookingAfter24Hours() {
        bookingService.approveBooking();
    }
}
