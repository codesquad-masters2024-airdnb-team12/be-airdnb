package com.airbnb.accommodation.unit.controller;

import com.airbnb.domain.accommodation.controller.AccommodationController;
import com.airbnb.domain.accommodation.controller.HostAccommodationController;
import com.airbnb.domain.accommodation.dto.response.AccommodationPageResponse;
import com.airbnb.domain.accommodation.dto.response.AccommodationResponse;
import com.airbnb.domain.accommodation.dto.request.AccommodationCreateRequest;
import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.common.AccommodationType;
import com.airbnb.domain.common.BuildingType;
import com.airbnb.domain.accommodation.service.AccommodationService;
import com.airbnb.domain.common.Address;
import com.airbnb.domain.common.Coordinate;
import com.airbnb.domain.member.entity.Member;
import com.airbnb.global.auth.jwt.JwtAuthFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = {AccommodationController.class, HostAccommodationController.class},
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, OAuth2ClientAutoConfiguration.class},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthFilter.class)})
class AccommodationControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper;

    private final FixtureMonkey sut;

    @MockBean
    AccommodationService accommodationService;

    @Autowired
    AccommodationControllerTest(MockMvc mvc, ObjectMapper mapper) {
        this.mvc = mvc;
        this.mapper = mapper;
        this.sut = FixtureMonkey.builder()
                .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                .plugin(new JakartaValidationPlugin())
                .build();
    }

    @DisplayName("회원 PK와 숙소 등록 요청이 주어지고, 숙소를 생성하면, 생성된 숙소를 저장 후 200 OK 응답을 반환한다.")
    @Test
    void givenMemberIdAccommodationCreateRequest_whenCreate_thenSaveAndReturnAccommodationResponse() throws Exception {
        // given
        Long memberId = 1L;
        AccommodationCreateRequest request = sut.giveMeBuilder(AccommodationCreateRequest.class)
                .set("accommodationType", AccommodationType.APARTMENT.name())
                .set("buildingType", BuildingType.ROOM.name())
                .sample();
        Accommodation accommodation = request.toEntity(mock(Member.class));
        AccommodationResponse response = AccommodationResponse.of(accommodation);
        given(accommodationService.create(eq(memberId), any(AccommodationCreateRequest.class)))
                .willReturn(response);

        // when
        ResultActions result = mvc.perform(
                post("/host/accommodations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @DisplayName("숙소 목록 조회 요청이 주어지면 숙소 50개를 조회해 200 OK를 응답한다.")
    @Test
    void givenAccommodationGetListRequest_whenGetList_thenReturnAccommodationPageResponse() throws Exception {
        // given
        int page = 1;
        int size = 50;
        String sort = "createdAt.DESC";
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));

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
                .initialDiscountCnt(3)
                .weeklyDiscountApplied(false)
                .monthlyDiscountApplied(true)
                .build();

        Page<Accommodation> accommodationPage = new PageImpl<>(Collections.nCopies(size, accommodation), pageable, size);
        AccommodationPageResponse response = AccommodationPageResponse.of(accommodationPage);

        given(accommodationService.getPage(any(Pageable.class))).willReturn(response);

        // when
        ResultActions result = mvc.perform(
                get("/accommodations?page=" + page + "&size=" + size + "&sort=" + sort)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(size)));
    }

    // TODO: 태그 감지
    // TODO: 할인 감지
}
