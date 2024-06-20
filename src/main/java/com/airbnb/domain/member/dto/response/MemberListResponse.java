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

    private List<MemberResponse> members;

    public static MemberListResponse from(List<Member> members) {
        return MemberListResponse.builder()
            .members(members.stream().map(MemberResponse::from).toList())
            .build();
    }
}
