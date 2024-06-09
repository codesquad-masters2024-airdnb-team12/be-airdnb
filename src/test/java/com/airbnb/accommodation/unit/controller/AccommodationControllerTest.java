package com.airbnb.accommodation.unit.controller;

import autoparams.AutoSource;
import com.airbnb.domain.accommodation.controller.AccommodationController;
import com.airbnb.domain.accommodation.dto.response.AccommodationResponse;
import com.airbnb.domain.accommodation.dto.request.AccommodationCreateRequest;
import com.airbnb.domain.accommodation.service.AccommodationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {AccommodationController.class})
class AccommodationControllerTest {

    private final MockMvc mvc;
    private final ObjectMapper mapper;

    @MockBean
    AccommodationService accommodationService;

    @Autowired
    AccommodationControllerTest(MockMvc mvc, ObjectMapper mapper) {
        this.mvc = mvc;
        this.mapper = mapper;
    }

    @DisplayName("숙소 등록 요청이 주어지고, 숙소를 생성하면, 생성된 숙소를 저장 후 200 OK 응답을 반환한다.")
    @ParameterizedTest
    @AutoSource
    void givenAccommodationCreateRequest_whenCreate_thenSaveAndReturnAccommodationResponse(
            AccommodationCreateRequest request) throws Exception {
        // given
        given(accommodationService.create(any(AccommodationCreateRequest.class)))
                .willReturn(any(AccommodationResponse.class));

        // when
        ResultActions result = mvc.perform(
                post("/accommodations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request))
        );

        // then
        result.andExpect(status().isOk());
    }
}
