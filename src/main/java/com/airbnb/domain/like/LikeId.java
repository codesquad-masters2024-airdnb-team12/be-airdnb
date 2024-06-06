package com.airbnb.domain.like;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class LikeId implements Serializable {

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long accommodationId;

    public LikeId(Long memberId, Long accommodationId) {
        this.memberId = memberId;
        this.accommodationId = accommodationId;
    }
}
