package com.airbnb.accommodation.unit.service;

import com.airbnb.domain.accommodation.dto.response.AccommodationPageResponse;
import com.airbnb.domain.accommodationFacility.entity.AccommodationFacility;
import com.airbnb.domain.accommodationFacility.repository.AccommodationFacilityRepository;
import com.airbnb.domain.common.Address;
import com.airbnb.domain.common.Coordinate;
import com.airbnb.domain.accommodation.dto.request.AccommodationCreateRequest;
import com.airbnb.domain.accommodation.dto.response.AccommodationResponse;
import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.common.AccommodationType;
import com.airbnb.domain.common.BuildingType;
import com.airbnb.domain.accommodation.repository.AccommodationRepository;
import com.airbnb.domain.accommodation.service.AccommodationService;
import com.airbnb.domain.facility.entity.Facility;
import com.airbnb.domain.facility.repository.FacilityRepository;
import com.airbnb.domain.member.entity.Member;
import com.airbnb.domain.member.repository.MemberRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AccommodationServiceTest {

    @InjectMocks
    AccommodationService accommodationService;

    @Mock
    MemberRepository memberRepository;

    @Mock
    AccommodationRepository accommodationRepository;

    @Mock
    FacilityRepository facilityRepository;

    @Mock
    AccommodationFacilityRepository accommodationFacilityRepository;

    FixtureMonkey sut;

    @BeforeEach
    void setup() {
        sut = FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .plugin(new JakartaValidationPlugin())
                .build();
    }

    @DisplayName("회원 PK와 숙소 등록을 위한 정보가 주어지고, 숙소를 생성하면, 숙소를 저장 후 저장된 숙소 정보를 반환한다.")
    @Test
    void givenMemberIdAndAccommodationCreateRequest_whenCreateAccommodation_thenSaveAndReturnAccommodationResponse() {
        // given
        Facility facility = mock(Facility.class);
        AccommodationCreateRequest request = sut.giveMeBuilder(AccommodationCreateRequest.class)
                .set("accommodationType", AccommodationType.APARTMENT.name())
                .set("buildingType", BuildingType.ROOM.name())
                .set("facilities", Set.of(facility.getId()))
                .sample();
        Member member = mock(Member.class);
        Accommodation accommodation = request.toEntity(member);
        AccommodationFacility accommodationFacility = AccommodationFacility.builder()
                .accommodation(accommodation)
                .facility(facility)
                .build();

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(accommodationRepository.save(any(Accommodation.class))).willReturn(accommodation);
        given(facilityRepository.findAllById(any())).willReturn(List.of(facility));
        given(accommodationFacilityRepository.saveAll(any())).willReturn(List.of(accommodationFacility));

        // when
        AccommodationResponse accommodationResponse = accommodationService.create(member.getId(), request);

        // then
        assertThat(accommodationResponse).isNotNull();
    }

    @DisplayName("숙소 목록 조회 요청이 들어오면 숙소를 50개 조회해 숙소 페이지를 응답한다.")
    @Test
    void givenAccommodationGetListRequest_whenGetAccommodationList_thenReturnAccommodationPageResponse() {
        // given
        int page = 1;
        int size = 50;
        String sort = "createdAt.DESC";

        Coordinate coordinate = mock(Coordinate.class);

        Accommodation accommodation = Accommodation.builder()
                .host(mock(Member.class))
                .name("Sample Accommodation")
                .address(mock(Address.class))
                .latitude(coordinate.getLatitude())
                .longitude(coordinate.getLongitude())
                .bedroom(2)
                .bed(2)
                .bath(1)
                .maxGuests(4)
                .description("Sample description")
                .accommodationType(AccommodationType.HOTEL)
                .buildingType(BuildingType.ALL)
                .accommodationFacilities(new HashSet<>())
                .costPerNight(100000)
                .initialDiscountApplied(true)
                .weeklyDiscountApplied(false)
                .monthlyDiscountApplied(true)
                .build();

        Page<Accommodation> accommodationPage = new PageImpl<>(Collections.nCopies(size, accommodation));
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));

        given(accommodationRepository.findAll(pageable)).willReturn(accommodationPage);

        // when
        AccommodationPageResponse response = accommodationService.getPage(pageable);

        // then
        assertThat(response.getAccommodationResponses().getContent()).hasSize(size);
    }
}
