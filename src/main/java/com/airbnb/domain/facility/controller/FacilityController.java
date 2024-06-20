package com.airbnb.domain.facility.controller;

import com.airbnb.domain.facility.dto.response.FacilityListResponse;
import com.airbnb.domain.facility.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/facilities")
@RestController
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityService facilityService;

    @GetMapping
    public ResponseEntity<FacilityListResponse> getList() {
        return ResponseEntity.ok(
                facilityService.getList()
        );
    }

    @GetMapping("/forSearch")
    public ResponseEntity<FacilityListResponse> getListForSearch() {
        return ResponseEntity.ok(
                facilityService.getListForSearch()
        );
    }
}