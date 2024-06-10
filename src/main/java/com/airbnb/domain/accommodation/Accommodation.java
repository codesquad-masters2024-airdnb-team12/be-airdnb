package com.airbnb.domain.accommodation;

import com.airbnb.domain.accommodationDiscount.AccommodationDiscount;
import com.airbnb.domain.common.Address;
import com.airbnb.domain.common.BaseTime;
import com.airbnb.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Accommodation extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", referencedColumnName = "member_id", nullable = false)
    private Member host;

    @Column(length = 120, nullable = false)
    private String name;

    @Embedded
    @Column(nullable = false)
    private Address address;

    @Column(nullable = false)
    private Point coordinate;
    private int bedroom;
    private int bed;
    private int bath;
    private int maxGuests;

    @Column(length = 1000)
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private AccommodationDiscount accommodationDiscount;

    @Column(length = 1_000_0000, nullable = false)
    private int costPerNight;
    private LocalDateTime deletedAt;
    private Boolean initialDiscountApplied;
    private Boolean weeklyDiscountApplied;
    private Boolean monthlyDiscountApplied;

    @Builder
    private Accommodation(Member host, String name, Address address, Point coordinate, int bedroom, int bed, int bath, int maxGuests, String description, AccommodationDiscount accommodationDiscount, int costPerNight, Boolean initialDiscountApplied, Boolean weeklyDiscountApplied, Boolean monthlyDiscountApplied) {
        this.host = host;
        this.name = name;
        this.address = address;
        this.coordinate = coordinate;
        this.bedroom = bedroom;
        this.bed = bed;
        this.bath = bath;
        this.maxGuests = maxGuests;
        this.description = description;
        this.accommodationDiscount = accommodationDiscount;
        this.costPerNight = costPerNight;
        this.initialDiscountApplied = initialDiscountApplied;
        this.weeklyDiscountApplied = weeklyDiscountApplied;
        this.monthlyDiscountApplied = monthlyDiscountApplied;
    }
}
