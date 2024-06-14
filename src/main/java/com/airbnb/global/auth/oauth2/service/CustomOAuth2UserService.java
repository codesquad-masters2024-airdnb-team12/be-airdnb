package com.airbnb.global.auth.oauth2.service;

import com.airbnb.domain.member.entity.Member;
import com.airbnb.domain.member.repository.MemberRepository;
import com.airbnb.global.auth.oauth2.user.OAuth2UserPrincipal;
import com.airbnb.global.auth.oauth2.user.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        // registration id 가져오기 (third-party id)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // userNameAttributeName(OAuth2 로그인 진행 시 키가 되는 필드값(Primary Key)) 가져오기
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
            .getUserInfoEndpoint().getUserNameAttributeName();

        // 유저 정보 dto 생성
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId, oAuth2User.getAttributes());

        // 회원가입 및 로그인
        Member member = saveOrUpdate(oAuth2UserInfo);

        // OAuth2User 로 반환
        return new OAuth2UserPrincipal(member, oAuth2User.getAttributes(), userNameAttributeName);
    }

    private Member saveOrUpdate(OAuth2UserInfo oAuth2UserInfo) {
        // 회원 정보가 업데이트 되었다면, 기존 DB에 저장된 회원의 이름을 업데이트해줍니다.
        Member member = memberRepository.findByEmail(oAuth2UserInfo.email())
            .map(entity -> entity.update(oAuth2UserInfo))
            .orElseGet(oAuth2UserInfo::toEntity);

        //만약에 DB에 등록된 이메일이 아니라면, save 하여 DB에 등록(회원가입)을 진행시켜준다.
        return memberRepository.save(member);
    }
}