package com.whitechapel.comics_collection_api.repository;

import com.whitechapel.comics_collection_api.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
}