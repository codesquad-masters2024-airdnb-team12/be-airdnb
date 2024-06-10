package com.airbnb.accommodation.unit.service;

import com.airbnb.domain.accommodation.dto.request.AccommodationCreateRequest;
import com.airbnb.domain.accommodation.dto.response.AccommodationResponse;
import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.accommodation.repository.AccommodationRepository;
import com.airbnb.domain.accommodation.service.AccommodationService;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
        AccommodationCreateRequest request = sut.giveMeOne(AccommodationCreateRequest.class);
        Member member = mock(Member.class);
        Accommodation accommodation = request.toEntity(member);

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(member));
        given(accommodationRepository.save(any(Accommodation.class))).willReturn(accommodation);

        // when
        AccommodationResponse accommodationResponse = accommodationService.create(member.getId(), request);

        // then
        assertThat(accommodationResponse).isNotNull();
    }
}
