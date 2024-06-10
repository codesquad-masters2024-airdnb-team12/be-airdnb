package com.airbnb.domain.member.dto.response;

import com.airbnb.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberResponse {

    private final String email;
    private final String imgUrl;
    private final String name;

    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
            .email(member.getEmail())
            .name(member.getName())
            .imgUrl(member.getImgUrl())
            .build();
    }
}