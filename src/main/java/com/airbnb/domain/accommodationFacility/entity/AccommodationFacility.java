package com.airbnb.domain.accommodationFacility.entity;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.facility.entity.Facility;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class AccommodationFacility implements Persistable<AccommodationFacilityId> {

    @EmbeddedId
    private AccommodationFacilityId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "accommodationId")
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "facilityId")
    @JoinColumn(name = "facility_id", nullable = false)
    private Facility facility;

    @Column(length = 100)
    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    private AccommodationFacility(Accommodation accommodation, Facility facility, String description) {
        this.accommodation = accommodation;
        this.facility = facility;
        this.description = description;
        this.id = new AccommodationFacilityId(accommodation.getId(), facility.getId());
    }

    @Override
    public boolean isNew() {
        return createdAt == null;
    }

    public void update(String description) {
        this.description = description;
    }
}
