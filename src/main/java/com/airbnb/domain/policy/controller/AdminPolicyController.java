package com.airbnb.domain.policy.controller;

import com.airbnb.domain.policy.dto.request.FeePolicyCreateRequest;
import com.airbnb.domain.policy.dto.response.FeePolicyResponse;
import com.airbnb.domain.policy.service.PolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin/policies")
@RestController
@RequiredArgsConstructor
public class AdminPolicyController {

    private final PolicyService policyService;

    @PostMapping("/fee")
    public ResponseEntity<FeePolicyResponse> create(@Valid @RequestBody FeePolicyCreateRequest request) {
        return ResponseEntity.ok(policyService.create(request));
    }
}
