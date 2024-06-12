package com.airbnb.accommodation.unit.controller;

import com.airbnb.domain.accommodation.controller.AccommodationController;
import com.airbnb.domain.accommodation.dto.response.AccommodationResponse;
import com.airbnb.domain.accommodation.dto.request.AccommodationCreateRequest;
import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.accommodation.entity.AccommodationType;
import com.airbnb.domain.accommodation.entity.BuildingType;
import com.airbnb.domain.accommodation.service.AccommodationService;
import com.airbnb.domain.member.entity.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = {AccommodationController.class})
class AccommodationControllerTest {

    private static final String url = "/accommodations";

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
        AccommodationResponse response = AccommodationResponse.from(accommodation);
        given(accommodationService.create(eq(memberId), any(AccommodationCreateRequest.class)))
                .willReturn(response);

        // when
        ResultActions result = mvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    // TODO: 태그 감지
    // TODO: 할인 감지
}
