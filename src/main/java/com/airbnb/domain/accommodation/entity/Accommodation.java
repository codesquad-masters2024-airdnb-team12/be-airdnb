package com.airbnb.domain.accommodation.entity;

import com.airbnb.domain.accommodationDiscount.AccommodationDiscount;
import com.airbnb.domain.accommodationFacility.entity.AccommodationFacility;
import com.airbnb.domain.common.AccommodationType;
import com.airbnb.domain.common.Address;
import com.airbnb.domain.common.BaseTime;
import com.airbnb.domain.common.BuildingType;
import com.airbnb.domain.member.entity.Member;
import com.airbnb.global.util.GeometryUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.locationtech.jts.geom.Point;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE accommodation SET deleted = true WHERE accommodation_id = ?")
@SQLRestriction("deleted IS NULL")
public class Accommodation extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accommodation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "host_id", referencedColumnName = "member_id", nullable = false)
    private Member host;

    @Column(length = 120, nullable = false)
    private String name;

    @Embedded
    @Column(nullable = false)
    private Address address;

    @Column(columnDefinition = "POINT SRID 4326", nullable = false)
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

    @OneToMany(mappedBy = "accommodation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Column(nullable = false)
    private Set<AccommodationFacility> accommodationFacilities;

    @OneToOne(mappedBy = "accommodation", cascade = CascadeType.PERSIST)
    private AccommodationDiscount accommodationDiscount;

    @Min(10_000)
    @Max(10_000_000)
    private int costPerNight;
    private Boolean deleted;
    private boolean initialDiscountApplied;
    private boolean weeklyDiscountApplied;
    private boolean monthlyDiscountApplied;

    @Builder
    private Accommodation(Member host, String name, Address address, double longitude, double latitude, int bedroom, int bed, int bath, int maxGuests, String description, AccommodationType accommodationType, BuildingType buildingType, Set<AccommodationFacility> accommodationFacilities, AccommodationDiscount accommodationDiscount, int costPerNight, Boolean initialDiscountApplied, Boolean weeklyDiscountApplied, Boolean monthlyDiscountApplied) {
        this.host = host;
        this.name = name;
        this.address = address;
        this.coordinate = GeometryUtil.createPoint(longitude, latitude);
        this.bedroom = bedroom;
        this.bed = bed;
        this.bath = bath;
        this.maxGuests = maxGuests;
        this.description = description;
        this.accommodationType = accommodationType;
        this.buildingType = buildingType;
        this.accommodationFacilities = accommodationFacilities == null ? new HashSet<>() : accommodationFacilities;
        this.accommodationDiscount = accommodationDiscount;
        this.costPerNight = costPerNight;
        this.initialDiscountApplied = initialDiscountApplied;
        this.weeklyDiscountApplied = weeklyDiscountApplied;
        this.monthlyDiscountApplied = monthlyDiscountApplied;
    }

    public void addAccommodationDiscount(AccommodationDiscount accommodationDiscount) {
        this.accommodationDiscount = accommodationDiscount;
    }

    public void updateAccommodationOverview(String name, int bedroom, int bed, int bath, int maxGuests, String description,
                                            Address address, Point point, AccommodationType accommodationType, BuildingType buildingType) {
        this.name = name;
        this.bedroom = bedroom;
        this.bed = bed;
        this.bath = bath;
        this.maxGuests = maxGuests;
        this.description = description;
        this.address = address == null ? this.address : address;
        this.coordinate = point == null ? this.coordinate : point;
        this.accommodationType = accommodationType == null ? this.accommodationType : accommodationType;
        this.buildingType = buildingType == null ? this.buildingType : buildingType;
    }

    public boolean isHost(String hostEmail) {
        return this.host.getEmail().equals(hostEmail);
    }
}
