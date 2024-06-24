package com.airbnb.domain.booking.entity;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.common.BaseTime;
import com.airbnb.domain.common.BookingStatus;
import com.airbnb.domain.member.entity.Member;
import com.airbnb.domain.payment.entity.Payment;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static com.airbnb.domain.common.BookingStatus.*;
import static com.airbnb.domain.common.PaymentStatus.COMPLETED;
import static com.airbnb.domain.common.PaymentStatus.WITHDRAWN;

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

    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
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

    public void approve() {
        this.status = CONFIRMED;
        this.payment.changeStatus(COMPLETED);
    }

    public void cancel() {
        this.status = CANCELED;
        this.payment.changeStatus(WITHDRAWN);
    }

    public void reject() {
        this.status = REJECTED;
        this.payment.changeStatus(WITHDRAWN);
    }

    public boolean isGuest(Long guestId) {
        return this.guest.hasEqualMemberId(guestId);
    }

    public boolean isHost(Long hostId) {
        return this.accommodation.isHost(hostId);
    }
}