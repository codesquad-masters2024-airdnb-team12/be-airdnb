package com.airbnb.domain.like;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes")
public class Like {

    @EmbeddedId
    private LikeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "memberId")
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId(value = "accommodationId")
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    @Builder
    private Like(Member member, Accommodation accommodation) {
        this.member = member;
        this.accommodation = accommodation;
        this.id = new LikeId(member.getId(), accommodation.getId());
    }
}
