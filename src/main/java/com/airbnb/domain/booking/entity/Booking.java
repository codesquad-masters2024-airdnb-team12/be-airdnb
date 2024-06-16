package com.airbnb.domain.booking.entity;

import static com.airbnb.domain.booking.entity.BookingStatus.*;
import static com.airbnb.domain.payment.entity.PaymentStatus.WITHDRAWN;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.common.BaseTime;
import com.airbnb.domain.member.entity.Member;
import com.airbnb.domain.payment.entity.Payment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Booking extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id", referencedColumnName = "member_id", nullable = false)
    private Member guest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate checkIn;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate checkOut;

    private int adults;
    private int children;
    private int infants;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Builder
    private Booking(Member guest, Accommodation accommodation, LocalDate checkIn, LocalDate checkOut, int adults, int children, int infants, BookingStatus status, Payment payment) {
        this.guest = guest;
        this.accommodation = accommodation;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.adults = adults;
        this.children = children;
        this.infants = infants;
        this.status = status;
        this.payment = payment;
    }

    public void changeStatus(BookingStatus status) {
        this.status = status;
    }

    public boolean needChangeUsingStatus(LocalDate today) {
        return status == CONFIRMED && today.isAfter(checkIn) && today.isBefore(checkOut);
    }

    public boolean needChangeCompletedStatus(LocalDate today) {
        return status == USING && today.isAfter(checkOut);
    }

    public void approve(Payment payment) {
        this.status = APPROVED;
        this.payment = payment;
    }

    public void cancel() {
        this.status = CANCELED;
        this.payment.changeStatus(WITHDRAWN);
    }

    public void reject() {
        this.status = BookingStatus.REJECTED;
        this.payment.changeStatus(WITHDRAWN);
    }
}