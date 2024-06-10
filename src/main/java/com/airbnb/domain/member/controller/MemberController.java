package com.airbnb.domain.member.controller;

import com.airbnb.domain.member.dto.request.SignUpRequest;
import com.airbnb.domain.member.dto.response.MemberResponse;
import com.airbnb.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponse> signUp(@Validated @RequestBody SignUpRequest signUpRequest) {
        MemberResponse createdMember = memberService.signUp(signUpRequest);
        return ResponseEntity.ok(createdMember);
    }
}