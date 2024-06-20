package com.airbnb.domain.facility.service;

import com.airbnb.domain.facility.dto.response.FacilityListResponse;
import com.airbnb.domain.facility.repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FacilityService {

    private final FacilityRepository facilityRepository;

    public FacilityListResponse getListForSearch() {
        return FacilityListResponse.of(facilityRepository.findAllByForSearchIsNotNull());
    }

    public FacilityListResponse getList() {
        return FacilityListResponse.of(facilityRepository.findAll());
    }
}
