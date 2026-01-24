package com.whitechapel.comics_collection_api.controller;

import com.whitechapel.comics_collection_api.entity.Series;
import com.whitechapel.comics_collection_api.repository.SeriesRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para gestionar Series de cómics.
 * Proporciona endpoints CRUD para la entidad Series.
 *
 * Buenas prácticas aplicadas:
 *  - @RestController + @RequestMapping
 *  - Constructor para inyección de dependencias (recomendado sobre @Autowired en campos)
 *  - @Valid para validar datos según anotaciones de Series.java
 *  - ResponseEntity para controlar códigos HTTP
 *  - Comentarios educativos explicando cada sección
 */
@RestController
@RequestMapping("/api/series")
public class SeriesController {

    private final SeriesRepository seriesRepository;

    /**
     * Inyección de dependencias vía constructor
     */
    @Autowired
    public SeriesController(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    // ------------------------
    // GET: Obtener todas las series
    // ------------------------
    @GetMapping
    public List<Series> getAll() {
        // Retorna todas las series en la BD
        return seriesRepository.findAll();
    }

    // ------------------------
    // GET: Obtener una serie por ID
    // ------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Series> getById(@PathVariable Long id) {
        Optional<Series> optional = seriesRepository.findById(id);
        return optional
                .map(ResponseEntity::ok)          // 200 OK si existe
                .orElse(ResponseEntity.notFound().build()); // 404 si no existe
    }

    // ------------------------
    // POST: Crear una nueva serie
    // ------------------------
    @PostMapping
    public ResponseEntity<Series> create(@Valid @RequestBody Series series) {
        // @Valid activa las validaciones de Series.java
        Series savedSeries = seriesRepository.save(series);
        // Devuelve 201 CREATED con la serie creada
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSeries);
    }

    // ------------------------
    // PUT: Actualizar una serie existente
    // ------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Series> update(@PathVariable Long id,
                                         @Valid @RequestBody Series details) {

        Optional<Series> optional = seriesRepository.findById(id);

        if (!optional.isPresent()) {
            // 404 Not Found si no existe
            return ResponseEntity.notFound().build();
        }

        Series series = optional.get();
        // Actualizamos solo los campos permitidos
        series.setTitle(details.getTitle());
        series.setNumbers(details.getNumbers());       // Campo de número total de cómics
        series.setPublisher(details.getPublisher());   // ManyToOne con Publisher

        Series updatedSeries = seriesRepository.save(series);
        return ResponseEntity.ok(updatedSeries);        // 200 OK con la serie actualizada
    }

    // ------------------------
    // DELETE: Eliminar una serie por ID
    // ------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!seriesRepository.existsById(id)) {
            // 404 Not Found si no existe
            return ResponseEntity.notFound().build();
        }

        // NOTA: Los comics asociados a esta serie serán eliminados automáticamente
        // si se usa orphanRemoval=true en Series.java
        seriesRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
