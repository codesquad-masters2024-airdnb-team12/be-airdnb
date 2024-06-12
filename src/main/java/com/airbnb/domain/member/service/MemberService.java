package com.airbnb.domain.member.service;

import com.airbnb.domain.member.dto.request.SignUpRequest;
import com.airbnb.domain.member.dto.request.UpdateMemberRequest;
import com.airbnb.domain.member.dto.response.MemberResponse;
import com.airbnb.domain.member.entity.Member;
import com.airbnb.domain.member.repository.MemberRepository;
import com.airbnb.global.security.PasswordEncoder;
import java.util.List;
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

    public List<MemberResponse> getAll() {
        List<Member> allMembers = memberRepository.findAll();
        return allMembers.stream().map(MemberResponse::of).toList();
    }

    public MemberResponse getById(Long targetId) throws IllegalArgumentException {
        Member targetMember = memberRepository.findById(targetId)
            .orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));

        return MemberResponse.of(targetMember);
    }

    public MemberResponse getByEmail(String email) throws IllegalArgumentException {
        Member targetMember = memberRepository.findByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));

        return MemberResponse.of(targetMember);
    }

    @Transactional
    public MemberResponse update(Long targetId, UpdateMemberRequest updateRequest) throws IllegalArgumentException {
        Member targetMember = memberRepository.findById(targetId)
            .orElseThrow(() -> new IllegalArgumentException("회원정보가 없습니다."));
        if (!isPasswordConfirmed(updateRequest.getPassword(), updateRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        Member updatedMember = targetMember.update(updateRequest, passwordEncoder);
        return MemberResponse.of(updatedMember);
    }

    @Transactional
    public void delete(Long targetId) throws IllegalArgumentException {
        memberRepository.deleteById(targetId);
    }

    private boolean isPasswordConfirmed(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}