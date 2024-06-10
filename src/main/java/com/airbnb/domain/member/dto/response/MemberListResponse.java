package com.airbnb.domain.member.dto.response;

import com.airbnb.domain.member.entity.Member;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberListResponse {

    private final List<MemberResponse> members;

    public static MemberListResponse of(List<Member> members) {
        return MemberListResponse.builder()
            .members(members.stream().map(MemberResponse::of).toList())
            .build();
    }
}
