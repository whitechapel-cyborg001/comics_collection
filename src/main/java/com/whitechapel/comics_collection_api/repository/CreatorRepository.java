package com.whitechapel.comics_collection_api.repository;

import com.whitechapel.comics_collection_api.entity.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository de la entidad Creator (Autor/Artista).
 * Proporciona acceso a la base de datos mediante Spring Data JPA.
 */
@Repository
public interface CreatorRepository extends JpaRepository<Creator, Long> {

    /**
     * Buscar un creator por su nombre exacto.
     */
    Optional<Creator> findByName(String name);

    /**
     * Buscar todos los creators cuyo nombre contenga un texto dado,
     * ignorando mayúsculas y minúsculas.
     */
    List<Creator> findByNameContainingIgnoreCase(String name);
}
