package com.airbnb.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LogInResponse {

    private final String accessToken;
    private final String refreshToken;

}
