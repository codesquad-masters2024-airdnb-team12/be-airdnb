package com.airbnb.domain.like;

import com.airbnb.domain.accommodation.Accommodation;
import com.airbnb.domain.member.Member;
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
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accomodation", nullable = false)
    private Accommodation accommodation;

    @Builder
    public Like(Member member, Accommodation accommodation) {
        this.member = member;
        this.accommodation = accommodation;
    }
}
