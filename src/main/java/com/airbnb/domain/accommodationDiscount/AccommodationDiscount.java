package com.airbnb.domain.accommodationDiscount;

import com.airbnb.domain.accommodation.entity.Accommodation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccommodationDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_discount_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;
    private int remainDiscountCnt;

    @Builder
    private AccommodationDiscount(Accommodation accommodation, int remainDiscountCnt) {
        this.accommodation = accommodation;
        this.remainDiscountCnt = remainDiscountCnt;
    }
}