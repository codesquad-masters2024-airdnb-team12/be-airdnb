package com.airbnb.global.auth.oauth2.user;

import com.airbnb.domain.common.LoginType;
import com.airbnb.domain.common.Role;
import com.airbnb.domain.member.entity.Member;
import java.util.Map;
import lombok.Builder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

@Builder
public record OAuth2UserInfo (
    String registrationId,
    String userId,
    String email,
    String name,
    String imgUrl
) {
    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        LoginType registrationType = LoginType.valueOf(registrationId.toUpperCase());
        return switch (registrationType) {  // registration 타입 별로 userInfo 생성
            case KAKAO -> ofKakao(attributes);
            case NAVER -> ofNaver(attributes);
            case GITHUB -> ofGithub(attributes);
            case GOOGLE -> ofGoogle(attributes);
            default -> throw new OAuth2AuthenticationException("");
        };
    }

    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        return null;
    }

    private static OAuth2UserInfo ofNaver(Map<String, Object> attributes) {
        return null;
    }

    private static OAuth2UserInfo ofGithub(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
            .registrationId("GITHUB")
            .userId(String.valueOf(attributes.get("login")))
            .email((String) attributes.get("email"))
            .name((String) attributes.get("name"))
            .imgUrl((String) attributes.get("avatar_url"))
            .build();
    }

    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return null;
    }

    public Member toEntity() {
        return Member.builder()
            .email(email)
            .loginType(LoginType.valueOf(registrationId))
            .role(Role.GUEST)
            .name(name)
            .imgUrl(imgUrl)
            .build();
    }
}
