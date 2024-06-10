package com.airbnb.domain.member.controller;

import com.airbnb.domain.member.dto.request.SignUpRequest;
import com.airbnb.domain.member.dto.response.MemberResponse;
import com.airbnb.domain.member.service.MemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getAll() {
        List<MemberResponse> allMembers = memberService.getAll();
        return ResponseEntity.ok(allMembers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getById(@PathVariable Long id) {
        MemberResponse targetMember = memberService.getById(id);
        return ResponseEntity.ok(targetMember);
    }
}