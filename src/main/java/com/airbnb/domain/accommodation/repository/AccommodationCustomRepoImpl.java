package com.airbnb.domain.accommodation.repository;

import com.airbnb.domain.accommodation.dto.response.AccommodationOverview;
import com.airbnb.domain.accommodation.dto.response.QAccommodationOverview;
import com.airbnb.domain.accommodation.entity.Accommodation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.airbnb.domain.accommodation.entity.QAccommodation.accommodation;
import static com.airbnb.domain.accommodationFacility.entity.QAccommodationFacility.accommodationFacility;
import static com.airbnb.domain.facility.entity.QFacility.facility;
import static com.airbnb.domain.member.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class AccommodationCustomRepoImpl implements AccommodationCustomRepo{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Accommodation> findDetailById(Long accommodationId) {
        Accommodation result = jpaQueryFactory.selectFrom(accommodation)
                .leftJoin(accommodation.accommodationFacilities, accommodationFacility).fetchJoin()
                .leftJoin(accommodationFacility.facility, facility).fetchJoin()
                .leftJoin(accommodation.host, member).fetchJoin()
                .where(accommodation.id.eq(accommodationId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<AccommodationOverview> findOverviewById(Long accommodationId) {
        QAccommodationOverview accommodationOverview = new QAccommodationOverview(
                accommodation.id,
                accommodation.host.id,
                accommodation.name,
                accommodation.bedroom,
                accommodation.bed,
                accommodation.bath,
                accommodation.maxGuests,
                accommodation.description,
                accommodation.address,
                accommodation.coordinate,
                accommodation.accommodationType,
                accommodation.buildingType
        );

        AccommodationOverview result = jpaQueryFactory
                .select(accommodationOverview)
                .from(accommodation)
                .innerJoin(accommodation.host, member)
                .where(accommodation.id.eq(accommodationId))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
