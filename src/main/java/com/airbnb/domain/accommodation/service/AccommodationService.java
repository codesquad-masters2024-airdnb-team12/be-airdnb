package com.airbnb.domain.accommodation.service;

import com.airbnb.domain.accommodation.dto.request.AccommodationCreateRequest;
import com.airbnb.domain.accommodation.dto.request.AccommodationFacilitiesRequest;
import com.airbnb.domain.accommodation.dto.request.AccommodationFacilityEditRequest;
import com.airbnb.domain.accommodation.dto.request.AccommodationOverviewEditRequest;
import com.airbnb.domain.accommodation.dto.response.*;
import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.accommodation.repository.AccommodationRepository;
import com.airbnb.domain.accommodationFacility.entity.AccommodationFacility;
import com.airbnb.domain.accommodationFacility.repository.AccommodationFacilityRepository;
import com.airbnb.domain.booking.repository.BookingRepository;
import com.airbnb.domain.facility.entity.Facility;
import com.airbnb.domain.facility.repository.FacilityRepository;
import com.airbnb.domain.member.entity.Member;
import com.airbnb.domain.member.repository.MemberRepository;
import com.airbnb.domain.policy.entity.DiscountPolicy;
import com.airbnb.domain.policy.repository.DiscountPolicyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final MemberRepository memberRepository;
    private final FacilityRepository facilityRepository;
    private final BookingRepository bookingRepository;
    private final AccommodationFacilityRepository accommodationFacilityRepository;
    private final DiscountPolicyRepository discountPolicyRepository;

    @Transactional
    public AccommodationResponse create(Long hostId, AccommodationCreateRequest request) {
        Member host = memberRepository.findById(hostId).orElseThrow();  // TODO : exception 처리

        Accommodation entity = request.toEntity(host);

        // 편의시설 조회
        if (request.getFacilities() != null) {
            Set<Long> facilityIds = request.getFacilities();
            List<Facility> facilities = facilityRepository.findAllById(facilityIds);

            List<Long> foundFacilityIds = facilities.stream().map(Facility::getId).toList();
            List<Long> missingFacilityIds = facilityIds.stream().filter(id -> !foundFacilityIds.contains(id)).toList();

            if (!missingFacilityIds.isEmpty()) {
                throw new IllegalArgumentException("존재하지 않는 편의시설입니다: " + missingFacilityIds);
            }

            // 숙소 편의시설 등록
            Set<AccommodationFacility> accommodationFacilities = facilities.stream()
                    .map(facility -> AccommodationFacility.builder()
                            .accommodation(entity)
                            .facility(facility)
                            .build())
                    .collect(Collectors.toSet());

            accommodationFacilityRepository.saveAll(accommodationFacilities);
        }

        // 첫 이용객 할인 적용 시
        if (entity.isInitialDiscountApplied()) {
            DiscountPolicy discountPolicy = discountPolicyRepository.findValidDiscountPolicy(LocalDate.now())
                    .orElseThrow(() -> new NoSuchElementException("할인 정책이 존재하지 않습니다."));
            entity.setInitialDiscountCnt(discountPolicy.getInitialDiscountCnt());
        }

        Accommodation accommodation = accommodationRepository.save(entity);

        return AccommodationResponse.of(accommodation);
    }

    public AccommodationPageResponse getPage(Pageable pageable) {
        Page<Accommodation> accommodationPage = accommodationRepository.findAll(pageable);
        return AccommodationPageResponse.of(accommodationPage);
    }

    public AccommodationDetailResponse getDetail(Long accommodationId) {
        Accommodation accommodation = accommodationRepository.findDetailById(accommodationId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 숙소입니다."));

        return AccommodationDetailResponse.of(accommodation);
    }

    public AccommodationOverview getOverview(Long hostId, Long accommodationId) {
        AccommodationOverview accommodationOverview = accommodationRepository.findOverviewById(accommodationId)
                .orElseThrow(() -> new NoSuchElementException("숙소가 존재하지 않습니다."));

        if (!accommodationOverview.getHostId().equals(hostId)) {
            throw new IllegalStateException("자신의 숙소 정보만 조회할 수 있습니다.");
        }

        return accommodationOverview;
    }

    public AccommodationFacilityListResponse getFacilities(Long hostId, Long accommodationId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 숙소입니다."));

        if (!accommodation.getHost().getId().equals(hostId)) {
            throw new IllegalStateException("자신의 숙소 정보만 조회할 수 있습니다.");
        }

        List<AccommodationFacility> accommodationFacilities = accommodationFacilityRepository.findAllByAccommodation(accommodation);

        return AccommodationFacilityListResponse.of(accommodationFacilities);
    }

    public AccommodationCost getCosts(Long hostId, Long accommodationId) {
        return null;
    }

    @Transactional
    public AccommodationOverview editOverview(Long hostId, Long accommodationId, AccommodationOverviewEditRequest request) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow(() -> new NoSuchElementException("숙소가 존재하지 않습니다."));

        if (!accommodation.getHost().getId().equals(hostId)) {
            throw new IllegalStateException("자신의 숙소 정보만 수정할 수 있습니다.");
        }

        // 위치, 건물, 숙소정보는 변경사항이 있고, 예약이 없을 때만 수정 가능
        boolean bookingExists = bookingRepository.existsByAccommodationId(accommodationId);
        request.updateEntity(accommodation, bookingExists);

        return AccommodationOverview.of(accommodation);
    }

    @Transactional
    public AccommodationFacilityResponse editAccommodationFacility(Long hostId, Long accommodationId, AccommodationFacilityEditRequest request) {
        AccommodationFacility accommodationFacility = accommodationFacilityRepository.findByAccommodationIdAndFacilityId(accommodationId, request.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 편의시설이 존재하지 않습니다."));

        if (!accommodationFacility.getAccommodation().getHost().getId().equals(hostId)) {
            throw new IllegalStateException("자신의 숙소 정보만 수정할 수 있습니다.");
        }

        accommodationFacility.update(request.getDescription());

        return AccommodationFacilityResponse.of(accommodationFacility);
    }

    @Transactional
    public AccommodationFacilityListResponse editAccommodationFacilities(Long hostId, Long accommodationId, AccommodationFacilitiesRequest request) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new NoSuchElementException("숙소가 존재하지 않습니다."));

        if (!accommodation.getHost().getId().equals(hostId)) {
            throw new IllegalStateException("자신의 숙소 정보만 수정할 수 있습니다.");
        }

        Set<Long> facilityIds = new HashSet<>(request.getFacilityIds());
        Set<AccommodationFacility> existingFacilities = accommodation.getAccommodationFacilities();

        // 기존 Facility ID
        Set<Long> existingFacilityIds = existingFacilities.stream()
                .map(f -> f.getFacility().getId())
                .collect(Collectors.toSet());

        Set<Long> idsToAdd = new HashSet<>(facilityIds);
        idsToAdd.removeAll(existingFacilityIds);

        Set<Long> idsToRemove = new HashSet<>(existingFacilityIds);
        idsToRemove.removeAll(facilityIds);

        accommodation.getAccommodationFacilities().removeIf(f -> idsToRemove.contains(f.getFacility().getId()));

        List<Facility> facilitiesToAdd = facilityRepository.findAllById(idsToAdd);
        if (facilitiesToAdd.size() != idsToAdd.size()) {
            throw new NoSuchElementException("존재하지 않는 편의시설이 포함되어 있습니다.");
        }

        Set<AccommodationFacility> newFacilities = facilitiesToAdd.stream()
                .map(facility -> AccommodationFacility.builder()
                        .accommodation(accommodation)
                        .facility(facility)
                        .build())
                .collect(Collectors.toSet());

        accommodation.getAccommodationFacilities().addAll(newFacilities);

        return AccommodationFacilityListResponse.of(accommodation.getAccommodationFacilities().stream().toList());
    }

    @Transactional
    public Long deactivateById(Long hostId, Long accommodationId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow(() -> new NoSuchElementException("숙소가 존재하지 않습니다."));

        if (!accommodation.getHost().getId().equals(hostId)) {
            throw new IllegalStateException("자신의 숙소만 비활성화할 수 있습니다.");
        }

        // TODO: 대기중인 예약이 없을 경우에만 비활성화 가능하도록 조건 추가

        accommodationRepository.delete(accommodation);

        return accommodation.getId();
    }
}
