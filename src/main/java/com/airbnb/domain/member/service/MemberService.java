package com.airbnb.domain.member.service;

import com.airbnb.domain.member.dto.request.SignUpRequest;
import com.airbnb.domain.member.dto.response.MemberResponse;
import com.airbnb.domain.member.entity.Member;
import com.airbnb.domain.member.repository.MemberRepository;
import com.airbnb.global.security.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponse signUp(SignUpRequest signUpRequest) throws IllegalArgumentException {
        if (memberRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new IllegalArgumentException("동일한 이메일이 이미 존재합니다.");
        }
        if (!isPasswordConfirmed(signUpRequest.getPassword(), signUpRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        Member savedMember = memberRepository.save(signUpRequest.toEntity(passwordEncoder));
        return MemberResponse.of(savedMember);
    }

    private boolean isPasswordConfirmed(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}