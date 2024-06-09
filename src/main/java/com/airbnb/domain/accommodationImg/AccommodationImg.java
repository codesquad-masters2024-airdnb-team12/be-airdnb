package com.airbnb.domain.accommodationImg;

import com.airbnb.domain.accommodation.entity.Accommodation;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccommodationImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_img_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    @Column(nullable = false)
    private String imgUrl;

    @Builder
    private AccommodationImg(Accommodation accommodation, String imgUrl) {
        this.accommodation = accommodation;
        this.imgUrl = imgUrl;
    }
}
