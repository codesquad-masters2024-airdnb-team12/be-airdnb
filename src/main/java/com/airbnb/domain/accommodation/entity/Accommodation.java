package com.airbnb.domain.accommodation.entity;

import com.airbnb.domain.AccommodationInfo.entity.AccommodationInfo;
import com.airbnb.domain.accommodationDiscount.AccommodationDiscount;
import com.airbnb.domain.accommodationFacility.AccommodationFacility;
import com.airbnb.domain.common.Address;
import com.airbnb.domain.common.BaseTime;
import com.airbnb.domain.facility.entity.Facility;
import com.airbnb.domain.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @Min(1)
    @Max(20)
    private int bedroom;

    @Min(0)
    @Max(20)
    private int bed;

    @Min(0)
    @Max(20)
    private int bath;

    @Min(1)
    @Max(20)
    private int maxGuests;

    @Column(length = 5000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccommodationType accommodationType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BuildingType buildingType;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.PERSIST)
    @Column(nullable = false)
    private Set<AccommodationFacility> accommodationFacilities;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.PERSIST)
    @Column(nullable = false)
    private Set<AccommodationInfo> accommodationInfos;

    @OneToOne(mappedBy = "accommodation", cascade = CascadeType.PERSIST)
    private AccommodationDiscount accommodationDiscount;

    @Min(10_000)
    @Max(10_000_000)
    private int costPerNight;
    private LocalDateTime deletedAt;
    private boolean initialDiscountApplied;
    private boolean weeklyDiscountApplied;
    private boolean monthlyDiscountApplied;

    @Builder
    private Accommodation(Member host, String name, Address address, Point coordinate, int bedroom, int bed, int bath, int maxGuests, String description, AccommodationType accommodationType, BuildingType buildingType, Set<AccommodationFacility> accommodationFacilities, Set<AccommodationInfo> accommodationInfos, AccommodationDiscount accommodationDiscount, int costPerNight, Boolean initialDiscountApplied, Boolean weeklyDiscountApplied, Boolean monthlyDiscountApplied) {
        this.host = host;
        this.name = name;
        this.address = address;
        this.coordinate = coordinate;
        this.bedroom = bedroom;
        this.bed = bed;
        this.bath = bath;
        this.maxGuests = maxGuests;
        this.description = description;
        this.accommodationType = accommodationType;
        this.buildingType = buildingType;
        this.accommodationFacilities = accommodationFacilities == null ? new HashSet<>() : accommodationFacilities;
        this.accommodationInfos = accommodationInfos == null ? new HashSet<>() : accommodationInfos;
        this.accommodationDiscount = accommodationDiscount;
        this.costPerNight = costPerNight;
        this.initialDiscountApplied = initialDiscountApplied;
        this.weeklyDiscountApplied = weeklyDiscountApplied;
        this.monthlyDiscountApplied = monthlyDiscountApplied;
    }

    public void addAccommodationDiscount(AccommodationDiscount accommodationDiscount) {
        this.accommodationDiscount = accommodationDiscount;
    }

    public void addAccommodationFacility(Facility facility) {
        this.accommodationFacilities.add(AccommodationFacility.builder()
                .accommodation(this)
                .facility(facility)
                .build());
    }

    public void addAccommodationFacilities(Set<Facility> facilities) {
        facilities.forEach(this::addAccommodationFacility);
    }

    public void addAccommodationInfos(Set<AccommodationInfo> accommodationInfos) {
        this.accommodationInfos.addAll(accommodationInfos);
    }

    public boolean isHost(String hostEmail) {
        return this.host.getEmail().equals(hostEmail);
    }
}
