package com.airbnb.domain.accommodationHashtag;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class AccommodationHashtagId {

    @Column(nullable = false)
    private Long accommodationId;

    @Column(nullable = false)
    private Long hashtagId;
}
