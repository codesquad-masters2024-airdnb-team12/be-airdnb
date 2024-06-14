package com.airbnb.domain.booking;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.common.BaseTime;
import com.airbnb.domain.member.entity.Member;
import com.airbnb.domain.payment.Payment;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    private int infants;
    private int children;
    private int adults;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate checkIn;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate checkOut;

    @Builder
    private Booking(Member guest, Accommodation accommodation, Payment payment, int infants, int children, int adults, LocalDate checkIn, LocalDate checkOut) {
        this.guest = guest;
        this.accommodation = accommodation;
        this.payment = payment;
        this.infants = infants;
        this.children = children;
        this.adults = adults;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}
