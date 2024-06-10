package com.airbnb.domain.member.controller;

import com.airbnb.domain.member.dto.request.SignUpRequest;
import com.airbnb.domain.member.dto.request.UpdateMemberRequest;
import com.airbnb.domain.member.dto.response.MemberListResponse;
import com.airbnb.domain.member.dto.response.MemberResponse;
import com.airbnb.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        MemberResponse createdMember = memberService.signUp(signUpRequest);
        return ResponseEntity.ok(createdMember);
    }

    @GetMapping
    public ResponseEntity<MemberListResponse> getAll() {
        MemberListResponse allMembers = memberService.getAll();
        return ResponseEntity.ok(allMembers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getById(@PathVariable Long id) {
        MemberResponse targetMember = memberService.getById(id);
        return ResponseEntity.ok(targetMember);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateById(@PathVariable Long id, @RequestBody @Valid UpdateMemberRequest updateRequest) {
        MemberResponse updatedMember = memberService.update(id, updateRequest);
        return ResponseEntity.ok(updatedMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.ok().build();
    }
}