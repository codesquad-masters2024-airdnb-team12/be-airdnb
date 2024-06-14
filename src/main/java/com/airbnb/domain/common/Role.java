package com.airbnb.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    ADMIN("ROLE_ADMIN", "관리자"),
    HOST("ROLE_HOST", "호스트"),
    GUEST("ROLE_GUEST", "게스트"),
    ;

    private final String key;
    private final String description;
}
