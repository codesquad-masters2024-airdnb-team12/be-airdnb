package com.airbnb.domain.accommodation.service;

import com.airbnb.domain.accommodation.dto.request.AccommodationCreateRequest;
import com.airbnb.domain.accommodation.dto.response.AccommodationResponse;
import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.accommodation.repository.AccommodationRepository;
import com.airbnb.domain.accommodationDiscount.AccommodationDiscount;
import com.airbnb.domain.hashtag.entity.Hashtag;
import com.airbnb.domain.hashtag.repository.HashtagRepository;
import com.airbnb.domain.member.entity.Member;
import com.airbnb.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final MemberRepository memberRepository;
    private final HashtagRepository hashtagRepository;

    @Transactional
    public AccommodationResponse create(Long hostId, AccommodationCreateRequest request) {
        Member host = memberRepository.findById(hostId).orElseThrow();  // TODO : exception 처리

        Accommodation entity = request.toEntity(host);

        // 태그 조회
        Set<String> names = new HashSet<>();
        names.add(request.getBuildingType());
        names.add(request.getAccommodationType());
        names.addAll(request.getAmenities());

        Set<Hashtag> hashtags = hashtagRepository.findByNameIn(names);

        Set<String> foundNames = hashtags.stream().map(Hashtag::getName).collect(Collectors.toSet());

        if (names.stream().anyMatch(name -> !foundNames.contains(name))) {
            throw new IllegalArgumentException("존재하지 않는 해시태그입니다");
        }

        // 숙소 태그 등록
        entity.addAccommodationHashtags(hashtags);

        // 첫 이용객 할인 적용 시
        if (entity.isInitialDiscountApplied()) {
            entity.addAccommodationDiscount(AccommodationDiscount.builder()
                    .accommodation(entity)
                    .remainDiscountCnt(3)   // TODO: 할인 정책 조회, 기본 할인 횟수 사용
                    .build());
        }

        Accommodation accommodation = accommodationRepository.save(entity);

        return AccommodationResponse.from(accommodation);
    }
}
