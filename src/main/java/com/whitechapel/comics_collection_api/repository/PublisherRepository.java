package com.whitechapel.comics_collection_api.repository;

import com.whitechapel.comics_collection_api.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * Repository de la entidad Publisher (Editorial).
 * Proporciona acceso a la base de datos mediante Spring Data JPA.
 *
 * Buenas prácticas aplicadas:
 *  - Extiende JpaRepository para heredar CRUD y paginación
 *  - @Repository para claridad y manejo de excepciones de Spring
 *  - Métodos personalizados documentados
 */
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    /**
     * Buscar un publisher por su nombre exacto.
     * Devuelve Optional<Publisher> para manejar casos donde no exista.
     */
    Optional<Publisher> findByName(String name);

    /**
     * Buscar todos los publishers cuyo nombre contenga un texto dado,
     * ignorando mayúsculas y minúsculas.
     */
    List<Publisher> findByNameContainingIgnoreCase(String name);
}
