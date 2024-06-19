package com.airbnb.domain.accommodation.repository;

import com.airbnb.domain.accommodation.dto.response.AccommodationFacilities;
import com.airbnb.domain.accommodation.dto.response.AccommodationOverview;
import com.airbnb.domain.accommodation.dto.response.QAccommodationOverview;
import com.airbnb.domain.accommodation.entity.Accommodation;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.airbnb.domain.AccommodationCustomizedFacility.entity.QAccommodationCustomizedFacility.accommodationCustomizedFacility;
import static com.airbnb.domain.accommodation.dto.response.AccommodationFacilities.*;
import static com.airbnb.domain.accommodation.entity.QAccommodation.accommodation;
import static com.airbnb.domain.accommodationDiscount.QAccommodationDiscount.accommodationDiscount;
import static com.airbnb.domain.accommodationFacility.QAccommodationFacility.accommodationFacility;
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
                .leftJoin(accommodation.accommodationCustomizedFacilities, accommodationCustomizedFacility).fetchJoin()
                .leftJoin(accommodation.accommodationDiscount, accommodationDiscount).fetchJoin()
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

    @Override
    public Optional<AccommodationFacilities> findFacilitiesById(Long accommodationId) {

        List<Tuple> result = jpaQueryFactory
                .select(
                        member.id,
                        facility.name,
                        facility.type,
                        accommodationCustomizedFacility.name,
                        accommodationCustomizedFacility.type
                )
                .distinct()
                .from(accommodation)
                .leftJoin(accommodation.host, member)
                .leftJoin(accommodation.accommodationFacilities, accommodationFacility)
                .leftJoin(accommodationFacility.facility, facility)
                .leftJoin(accommodation.accommodationCustomizedFacilities, accommodationCustomizedFacility)
                .where(accommodation.id.eq(accommodationId))
                .fetch();

        if (result.isEmpty()) {
            return Optional.empty();
        }

        Long hostId = result.get(0).get(member.id);
        Set<FacilityInfo> facilityInfos = result.stream()
                .filter(tuple -> tuple.get(facility.name) != null && tuple.get(facility.type) != null)
                .map(tuple -> new FacilityInfo(tuple.get(facility.name), tuple.get(facility.type)))
                .collect(Collectors.toSet());

        Set<CustomizedFacilityInfo> customizedFacilityInfos = result.stream()
                .filter(tuple -> tuple.get(accommodationCustomizedFacility.name) != null && tuple.get(accommodationCustomizedFacility.type) != null)
                .map(tuple -> new CustomizedFacilityInfo(tuple.get(accommodationCustomizedFacility.name), tuple.get(accommodationCustomizedFacility.type)))
                .collect(Collectors.toSet());

        AccommodationFacilities facilities = new AccommodationFacilities(hostId, facilityInfos, customizedFacilityInfos);
        return Optional.of(facilities);
    }
}
