package com.airbnb.domain.accommodationHashtag;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class AccommodationHashtagId implements Serializable {

    @Column(nullable = false)
    private Long accommodationId;

    @Column(nullable = false)
    private Long hashtagId;

    public AccommodationHashtagId(Long accommodationId, Long hashtagId) {
        this.accommodationId = accommodationId;
        this.hashtagId = hashtagId;
    }
}
