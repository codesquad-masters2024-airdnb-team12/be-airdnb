package com.airbnb.global.auth.oauth2.user;

import com.airbnb.domain.member.entity.Member;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public record OAuth2UserPrincipal (
    Member member,
    Map<String, Object> attributes,
    String attributeKey) implements OAuth2User, UserDetails {

    @Override
    public String getName() {
        return (String) attributes.get(attributeKey);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getPassword() {
        return member.getEncodedPassword();
    }

    @Override
    public String getUsername() {
        return member.getName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(member.getRole().getKey()));
    }
}
