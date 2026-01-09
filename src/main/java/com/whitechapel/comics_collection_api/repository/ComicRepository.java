package com.whitechapel.comics_collection_api.repository;

import com.whitechapel.comics_collection_api.entity.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComicRepository extends JpaRepository<Comic, Long> {

    // Búsquedas útiles para tu colección
    List<Comic> findBySeriesId(Long seriesId);
    List<Comic> findByOwnedTrueOrderByPurchaseDateDesc();
    List<Comic> findByTitleContainingIgnoreCase(String title);
}