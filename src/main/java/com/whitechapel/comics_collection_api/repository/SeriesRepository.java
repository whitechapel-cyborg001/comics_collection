package com.whitechapel.comics_collection_api.repository;

import com.whitechapel.comics_collection_api.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository de la entidad Series.
 * Proporciona acceso a la base de datos mediante Spring Data JPA.
 */
@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {

    /**
     * Buscar todas las series de un publisher específico por ID.
     */
    List<Series> findByPublisherId(Long publisherId);

    /**
     * Buscar una serie por su título exacto.
     */
    Optional<Series> findByTitle(String title);

    /**
     * Buscar todas las series cuyo título contenga un texto dado,
     * ignorando mayúsculas y minúsculas.
     */
    List<Series> findByTitleContainingIgnoreCase(String title);
}
