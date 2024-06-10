package com.airbnb.domain.accommodationHashtag;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.hashtag.entity.Hashtag;
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
public class AccommodationHashtag implements Persistable<AccommodationHashtagId> {

    @EmbeddedId
    private AccommodationHashtagId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "accommodationId")
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "hashtagId")
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashtag;

    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    private AccommodationHashtag(Accommodation accommodation, Hashtag hashtag) {
        this.accommodation = accommodation;
        this.hashtag = hashtag;
        this.id = new AccommodationHashtagId(accommodation.getId(), hashtag.getId());
    }

    @Override
    public boolean isNew() {
        return createdAt == null;
    }
}
