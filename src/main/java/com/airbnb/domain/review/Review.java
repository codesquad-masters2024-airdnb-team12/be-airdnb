package com.airbnb.domain.review;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.common.BaseTime;
import com.airbnb.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id", referencedColumnName = "member_id", nullable = false)
    private Member guest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accomodation_id", nullable = false)
    private Accommodation accommodation;

    @Column(nullable = false)
    private Double rating;
    private String content;
    private LocalDateTime deletedAt;

    @Builder
    private Review(Member guest, Accommodation accommodation, Double rating, String content) {
        this.guest = guest;
        this.accommodation = accommodation;
        this.rating = rating;
        this.content = content;
    }
}