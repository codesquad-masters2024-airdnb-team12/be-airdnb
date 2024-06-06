package com.airbnb.domain.hashtag;

import com.airbnb.domain.common.BaseTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HashtagType type;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String icon;

    @Builder
    private Hashtag(HashtagType type, String name, String icon) {
        this.type = type;
        this.name = name;
        this.icon = icon;
    }
}
