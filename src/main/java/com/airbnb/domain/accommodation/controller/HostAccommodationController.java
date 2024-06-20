package com.airbnb.domain.accommodation.controller;

import com.airbnb.domain.accommodation.dto.request.AccommodationCreateRequest;
import com.airbnb.domain.accommodation.dto.request.AccommodationFacilitiesRequest;
import com.airbnb.domain.accommodation.dto.request.AccommodationFacilityEditRequest;
import com.airbnb.domain.accommodation.dto.request.AccommodationOverviewEditRequest;
import com.airbnb.domain.accommodation.dto.response.*;
import com.airbnb.domain.accommodation.service.AccommodationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RequestMapping("/host/accommodations")
@RestController
@RequiredArgsConstructor
public class HostAccommodationController {

    private final AccommodationService accommodationService;

    @PostMapping
    public ResponseEntity<AccommodationResponse> create(@Valid @RequestBody AccommodationCreateRequest request) {
        // TODO: member 정보 파라미터로 받도록 수정
        Long hostId = 1L;

        return ResponseEntity.ok(
                accommodationService.create(hostId, request)
        );
    }

    @GetMapping("/{accommodationId}/overview")
    public ResponseEntity<AccommodationOverview> getOverview(
            @PathVariable Long accommodationId
    ) {
        Long hostId = 1L;

        return ResponseEntity.ok(
                accommodationService.getOverview(hostId, accommodationId)
        );
    }

    @GetMapping("/{accommodationId}/facilities")
    public ResponseEntity<AccommodationFacilityListResponse> getFacilities(
            @PathVariable Long accommodationId
    ) {
        Long hostId = 1L;

        return ResponseEntity.ok(
                accommodationService.getFacilities(hostId, accommodationId)
        );
    }

    @GetMapping("/{accommodationId}/costs")
    public ResponseEntity<AccommodationCost> getCosts(
            @PathVariable Long accommodationId
    ) {
        Long hostId = 1L;

        return ResponseEntity.ok(
                accommodationService.getCosts(hostId, accommodationId)
        );
    }

    @PatchMapping("/{accommodationId}/overview")
    public ResponseEntity<AccommodationOverview> editOverview(
            @PathVariable Long accommodationId,
            @Valid @RequestBody AccommodationOverviewEditRequest request
    ) {
        Long hostId = 1L;

        return ResponseEntity.ok(
                accommodationService.editOverview(hostId, accommodationId, request)
        );
    }

    @PatchMapping("/{accommodationId}/facility")
    public ResponseEntity<AccommodationFacilityResponse> editFacility(
            @PathVariable Long accommodationId,
            @Valid @RequestBody AccommodationFacilityEditRequest request
            ) {
        Long hostId = 1L;

        return ResponseEntity.ok(
                accommodationService.editAccommodationFacility(hostId, accommodationId, request)
        );
    }

    @PatchMapping("/{accommodationId}/facilities")
    public ResponseEntity<AccommodationFacilityListResponse> editFacilities(
            @PathVariable Long accommodationId,
            @Valid @RequestBody AccommodationFacilitiesRequest request
            ) {
        Long hostId = 1L;

        return ResponseEntity.ok(
                accommodationService.editAccommodationFacilities(hostId, accommodationId, request)
        );
    }

    @DeleteMapping("/{accommodationId}")
    public ResponseEntity<Map<String, Long>> deactivateById(@PathVariable Long accommodationId) {
        Long hostId = 1L;

        return ResponseEntity.ok(
                Collections.singletonMap("id", accommodationService.deactivateById(hostId, accommodationId))
        );
    }
}
