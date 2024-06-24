package com.airbnb.domain.policy.controller;

import com.airbnb.domain.policy.dto.request.DiscountPolicyCreateRequest;
import com.airbnb.domain.policy.dto.request.FeePolicyCreateRequest;
import com.airbnb.domain.policy.dto.response.DiscountPolicyListResponse;
import com.airbnb.domain.policy.dto.response.DiscountPolicyResponse;
import com.airbnb.domain.policy.dto.response.FeePolicyListResponse;
import com.airbnb.domain.policy.dto.response.FeePolicyResponse;
import com.airbnb.domain.policy.service.PolicyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/policies")
@RestController
@RequiredArgsConstructor
public class AdminPolicyController {

    private final PolicyService policyService;

    @PostMapping("/fee")
    public ResponseEntity<FeePolicyResponse> createFeePolicy(@Valid @RequestBody FeePolicyCreateRequest request) {
        return ResponseEntity.ok(policyService.createFeePolicy(request));
    }

    @PostMapping("/discount")
    public ResponseEntity<DiscountPolicyResponse> createDiscountPolicy(@Valid @RequestBody DiscountPolicyCreateRequest request) {
        return ResponseEntity.ok(policyService.createDiscountPolicy(request));
    }

    @GetMapping("/fee")
    public ResponseEntity<FeePolicyListResponse> getFeePolicyList() {
        return ResponseEntity.ok(policyService.getFeePolicyList());
    }

    @GetMapping("/discount")
    public ResponseEntity<DiscountPolicyListResponse> getDiscountPolicyList() {
        return ResponseEntity.ok(policyService.getDiscountPolicyList());
    }
}
