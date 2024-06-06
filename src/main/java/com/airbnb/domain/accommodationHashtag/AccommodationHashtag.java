package com.airbnb.domain.accommodationHashtag;

import com.airbnb.domain.accommodation.Accommodation;
import com.airbnb.domain.hashtag.Hashtag;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccommodationHashtag {

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

    @Builder
    private AccommodationHashtag(Accommodation accommodation, Hashtag hashtag) {
        this.accommodation = accommodation;
        this.hashtag = hashtag;
        this.id = new AccommodationHashtagId(accommodation.getId(), hashtag.getId());
    }
}
