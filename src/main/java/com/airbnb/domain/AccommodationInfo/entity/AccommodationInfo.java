package com.airbnb.domain.AccommodationInfo.entity;

import com.airbnb.domain.accommodation.entity.Accommodation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccommodationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    @Column(nullable = false, length = 30)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InfoType type;

    @Builder
    private AccommodationInfo(Accommodation accommodation, String name, InfoType type) {
        this.accommodation = accommodation;
        this.name = name;
        this.type = type;
    }
}
