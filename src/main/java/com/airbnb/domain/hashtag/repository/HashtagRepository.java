package com.airbnb.domain.hashtag.repository;

import com.airbnb.domain.hashtag.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    Set<Hashtag> findByNameIn(Set<String> names);
}