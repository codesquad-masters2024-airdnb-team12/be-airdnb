package com.airbnb.domain.member.service;

import com.airbnb.domain.member.dto.request.SignUpRequest;
import com.airbnb.domain.member.dto.request.UpdateMemberRequest;
import com.airbnb.domain.member.dto.response.MemberListResponse;
import com.airbnb.domain.member.dto.response.MemberResponse;
import com.airbnb.domain.member.entity.Member;
import com.airbnb.domain.member.repository.MemberRepository;
import com.airbnb.global.auth.jwt.JwtUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public MemberResponse signUp(SignUpRequest signUpRequest) {
        if (memberRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new IllegalArgumentException("동일한 이메일이 이미 존재합니다.");
        }

        Member newMember = memberRepository.save(signUpRequest.toEntity(passwordEncoder));
        return MemberResponse.from(newMember);
    }

    public MemberListResponse getAll() {
        List<Member> allMembers = memberRepository.findAll();
        return MemberListResponse.from(allMembers);
    }

    public MemberResponse getById(Long targetId) {
        Member targetMember = memberRepository.findById(targetId)
            .orElseThrow(() -> new IllegalArgumentException("해당 회원정보가 없습니다."));

        return MemberResponse.from(targetMember);
    }

    public MemberResponse getByEmail(String email) {
        Member targetMember = memberRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("해당 회원정보가 없습니다."));

        return MemberResponse.from(targetMember);
    }

    @Transactional
    public MemberResponse update(Long targetId, UpdateMemberRequest updateRequest) {
        Member targetMember = memberRepository.findById(targetId)
            .orElseThrow(() -> new IllegalArgumentException("해당 회원정보가 없습니다."));

        Member updatedMember = targetMember.update(updateRequest, passwordEncoder);
        return MemberResponse.from(updatedMember);
    }

    @Transactional
    public void delete(Long targetId) {
        memberRepository.deleteById(targetId);
    }
}