package com.airbnb.domain.accommodation.service;

import com.airbnb.domain.accommodation.dto.request.AccommodationCreateRequest;
import com.airbnb.domain.accommodation.dto.request.AccommodationOverviewEditRequest;
import com.airbnb.domain.accommodation.dto.response.*;
import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.accommodation.repository.AccommodationRepository;
import com.airbnb.domain.accommodationDiscount.AccommodationDiscount;
import com.airbnb.domain.booking.repository.BookingRepository;
import com.airbnb.domain.facility.entity.Facility;
import com.airbnb.domain.facility.repository.FacilityRepository;
import com.airbnb.domain.member.entity.Member;
import com.airbnb.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final MemberRepository memberRepository;
    private final FacilityRepository facilityRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    public AccommodationResponse create(Long hostId, AccommodationCreateRequest request) {
        Member host = memberRepository.findById(hostId).orElseThrow();  // TODO : exception 처리

        Accommodation entity = request.toEntity(host);

        // 태그 조회
        Set<String> names = new HashSet<>(request.getFacilities());
        Set<Facility> facilities = facilityRepository.findByNameIn(names);
        Set<String> foundNames = facilities.stream().map(Facility::getName).collect(Collectors.toSet());

        if (names.stream().anyMatch(name -> !foundNames.contains(name))) {
            throw new IllegalArgumentException("존재하지 않는 편의시설입니다");
        }

        // 숙소 태그 등록
        entity.addAccommodationFacilities(facilities);

        // 숙소 정보 등록
        entity.addAccommodationCustomizedFacilities(
                request.getInfo().stream().map(i -> i.toEntity(entity))
                        .collect(Collectors.toSet())
        );

        // 첫 이용객 할인 적용 시
        if (entity.isInitialDiscountApplied()) {
            entity.addAccommodationDiscount(AccommodationDiscount.builder()
                    .accommodation(entity)
                    .remainDiscountCnt(3)   // TODO: 할인 정책 조회, 기본 할인 횟수 사용
                    .build());
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

        Map<String, List<String>> groupedFacilities = Stream.concat(
                accommodation.getAccommodationFacilities().stream()
                        .map(af -> new AbstractMap.SimpleEntry<>(af.getFacility().getType().getDescription(), af.getFacility().getName())),
                accommodation.getAccommodationCustomizedFacilities().stream()
                        .map(acf -> new AbstractMap.SimpleEntry<>(acf.getType().getDescription(), acf.getName()))
        ).collect(Collectors.groupingBy(
                Map.Entry::getKey,
                Collectors.mapping(Map.Entry::getValue, Collectors.toList())
        ));

        return AccommodationDetailResponse.of(accommodation, groupedFacilities);
    }

    public AccommodationOverview getOverview(Long hostId, Long accommodationId) {
        AccommodationOverview accommodationOverview = accommodationRepository.findOverviewById(accommodationId)
                .orElseThrow(() -> new NoSuchElementException("숙소가 존재하지 않습니다."));

        if (!accommodationOverview.getHostId().equals(hostId)) {
            throw new IllegalStateException("자신의 숙소 정보만 조회할 수 있습니다.");
        }

        return accommodationOverview;
    }

    public AccommodationFacilities getFacilities(Long hostId, Long accommodationId) {
        AccommodationFacilities accommodationFacilities = accommodationRepository.findFacilitiesById(accommodationId)
                .orElseThrow(() -> new NoSuchElementException("숙소가 존재하지 않습니다."));

        if (!accommodationFacilities.getHostId().equals(hostId)) {
            throw new IllegalStateException("자신의 숙소 정보만 조회할 수 있습니다.");
        }

        accommodationFacilities.setFacilities(
                Stream.concat(
                        accommodationFacilities.getFacilitySet().stream()
                                .map(fs -> new AbstractMap.SimpleEntry<>(fs.getType().getDescription(), fs.getName())),
                        accommodationFacilities.getCustomizedFacilitySet().stream()
                                .map(cfs -> new AbstractMap.SimpleEntry<>(cfs.getType().getDescription(), cfs.getName()))
                ).collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toSet())
                ))
        );

        return accommodationFacilities;
    }

    public AccommodationCost getCosts(Long hostId, Long accommodationId) {
        return null;
    }

    @Transactional
    public AccommodationOverview editOverview(Long hostId, Long accommodationId, AccommodationOverviewEditRequest request) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow(() -> new NoSuchElementException("숙소가 존재하지 않습니다."));

        if (!accommodation.getHost().getId().equals(hostId)) {
            throw new IllegalStateException("자신의 숙소 정보만 조회할 수 있습니다.");
        }

        // 위치, 건물, 숙소정보는 변경사항이 있고, 예약이 없을 때만 수정 가능
        boolean bookingExists = bookingRepository.existsByAccommodationId(accommodationId);
        request.updateEntity(accommodation, bookingExists);

        return AccommodationOverview.of(accommodation);
    }
}
