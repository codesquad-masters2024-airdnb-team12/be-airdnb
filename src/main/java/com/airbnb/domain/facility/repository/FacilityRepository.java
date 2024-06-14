package com.airbnb.domain.facility.repository;

import com.airbnb.domain.facility.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    Set<Facility> findByNameIn(Set<String> names);
}