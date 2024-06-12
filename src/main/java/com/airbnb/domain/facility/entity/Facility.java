package com.airbnb.domain.facility.entity;

import com.airbnb.domain.common.FacilityType;
import com.airbnb.domain.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Facility extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FacilityType type;

    @Column(nullable = false)
    private String name;

    @Builder
    private Facility(FacilityType type, String name) {
        this.type = type;
        this.name = name;
    }
}
