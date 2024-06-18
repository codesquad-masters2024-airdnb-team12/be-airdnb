package com.airbnb.domain.accommodation.repository;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.airbnb.domain.AccommodationInfo.entity.QAccommodationCustomizedFacility.accommodationCustomizedFacility;
import static com.airbnb.domain.accommodation.entity.QAccommodation.accommodation;
import static com.airbnb.domain.accommodationFacility.QAccommodationFacility.accommodationFacility;
import static com.airbnb.domain.facility.entity.QFacility.facility;

@Repository
@RequiredArgsConstructor
public class AccommodationCustomRepoImpl implements AccommodationCustomRepo{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Accommodation> findDetailById(Long accommodationId) {
        Accommodation result = jpaQueryFactory.selectFrom(accommodation)
                .leftJoin(accommodation.accommodationFacilities, accommodationFacility).fetchJoin()
                .leftJoin(accommodationFacility.facility, facility).fetchJoin()
                .leftJoin(accommodation.accommodationCustomizedFacilities, accommodationCustomizedFacility).fetchJoin()
                .where(accommodation.id.eq(accommodationId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
