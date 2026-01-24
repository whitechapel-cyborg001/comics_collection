package com.whitechapel.comics_collection_api.repository;

import com.whitechapel.comics_collection_api.entity.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository de la entidad Comic.
 * Proporciona acceso a la base de datos mediante Spring Data JPA.
 *
 * Buenas prácticas aplicadas:
 *  - Extiende JpaRepository para heredar CRUD y paginación
 *  - @Repository para claridad y manejo de excepciones de Spring
 *  - Métodos personalizados documentados
 */
@Repository
public interface ComicRepository extends JpaRepository<Comic, Long> {

    /**
     * Obtiene todos los cómics de una serie específica por su ID.
     * Ejemplo: findBySeriesId(1L) devuelve todos los comics de la serie con id=1.
     *
     * Spring Data construye automáticamente la consulta SQL
     * gracias a la convención de nombres "findBySeriesId".
     */
    List<Comic> findBySeriesId(Long seriesId);

    /**
     * Obtiene todos los cómics que tienes en propiedad, ordenados
     * por fecha de compra descendente (los más recientes primero).
     */
    List<Comic> findByOwnedTrueOrderByPurchaseDateDesc();

    /**
     * Busca cómics cuyo título contenga el texto dado,
     * ignorando mayúsculas/minúsculas.
     * Ejemplo: findByTitleContainingIgnoreCase("spider") devuelve "Amazing Spider-Man".
     */
    List<Comic> findByTitleContainingIgnoreCase(String title);
}
