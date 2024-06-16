package com.airbnb.global.scheduler;

import com.airbnb.domain.booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingScheduler {

    private final BookingService bookingService;

    // 매일 자정에 실행
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateBookingStatus() {
        bookingService.updateStatusByDate();
    }
}
