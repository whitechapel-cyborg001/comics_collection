package com.whitechapel.comics_collection_api.repository;

import com.whitechapel.comics_collection_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository de la entidad User.
 * Proporciona acceso a la base de datos mediante Spring Data JPA.
 *
 * Buenas prácticas:
 *  - Extiende JpaRepository para CRUD y paginación
 *  - @Repository para claridad
 *  - findByUsername devuelve Optional<User> para manejar usuarios no existentes
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Buscar un usuario por su username.
     * Devuelve Optional<User> para evitar NullPointerException si no existe.
     */
    Optional<User> findByUsername(String username);
}
