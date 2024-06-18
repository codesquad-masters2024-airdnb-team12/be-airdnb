package com.airbnb.domain.accommodation.controller;

import com.airbnb.domain.accommodation.dto.response.AccommodationDetailResponse;
import com.airbnb.domain.accommodation.dto.response.AccommodationPageResponse;
import com.airbnb.domain.accommodation.service.AccommodationService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/accommodations")
@RestController
@RequiredArgsConstructor
@Validated
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping
    public ResponseEntity<AccommodationPageResponse> getList(
            @Min(1) @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @Min(0) @RequestParam(name = "size", defaultValue = "50", required = false) int size,
            @Pattern(regexp = "^.+\\.(ASC|DESC)$", message = "sort 파라미터 값은 'property.DIRECTION' 형식이어야 합니다.")
            @RequestParam(name = "sort", defaultValue = "createdAt.DESC", required = false) String sort) {

        String[] sortParams = sort.split("\\.");
        String sortProperty = sortParams[0];
        Sort.Direction sortDirection = Sort.Direction.valueOf(sortParams[1]);
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sortDirection, sortProperty));

        return ResponseEntity.ok(
                accommodationService.getPage(pageable)
        );
    }

    @GetMapping("/{accommodationId}")
    public ResponseEntity<AccommodationDetailResponse> getDetail(@PathVariable Long accommodationId) {
        return ResponseEntity.ok(
                accommodationService.getDetail(accommodationId)
        );
    }
}