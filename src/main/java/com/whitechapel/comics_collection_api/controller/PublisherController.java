package com.whitechapel.comics_collection_api.controller;

import com.whitechapel.comics_collection_api.entity.Publisher;
import com.whitechapel.comics_collection_api.repository.PublisherRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller REST para gestionar Publishers (Editoriales).
 * Proporciona endpoints CRUD para la entidad Publisher.
 *
 * Buenas prácticas aplicadas:
 *  - @RestController + @RequestMapping
 *  - Inyección de dependencias vía constructor (recomendado sobre @Autowired en campo)
 *  - @Valid para validar datos según anotaciones de la entidad
 *  - ResponseEntity para controlar códigos HTTP
 *  - Comentarios educativos explicando cada sección
 */
@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    private final PublisherRepository publisherRepository;

    /**
     * Inyección de dependencias vía constructor.
     * Mejora testabilidad y evita problemas con @Autowired en campos.
     */
    @Autowired
    public PublisherController(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    // ------------------------
    // GET: Obtener todos los publishers (con paginación opcional)
    // ------------------------
    @GetMapping
    public Page<Publisher> getAll(Pageable pageable) {
        // Pageable permite solicitar páginas con parámetros: ?page=0&size=10
        return publisherRepository.findAll(pageable);
    }

    // ------------------------
    // GET: Obtener un publisher por ID
    // ------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getById(@PathVariable Long id) {
        Optional<Publisher> optional = publisherRepository.findById(id);
        return optional
                .map(ResponseEntity::ok) // 200 OK si existe
                .orElse(ResponseEntity.notFound().build()); // 404 si no existe
    }

    // ------------------------
    // POST: Crear un nuevo publisher
    // ------------------------
    @PostMapping
    public ResponseEntity<Publisher> create(@Valid @RequestBody Publisher publisher) {
        Publisher savedPublisher = publisherRepository.save(publisher);
        // Devuelve 201 CREATED y el publisher creado
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPublisher);
    }

    // ------------------------
    // PUT: Actualizar un publisher existente
    // ------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Publisher> update(@PathVariable Long id,
                                            @Valid @RequestBody Publisher details) {

        Optional<Publisher> optional = publisherRepository.findById(id);

        if (!optional.isPresent()) {
            // 404 Not Found si no existe
            return ResponseEntity.notFound().build();
        }

        Publisher publisher = optional.get();
        // Solo actualizamos campos permitidos (nombre)
        publisher.setName(details.getName());
        // Guardamos cambios y devolvemos 200 OK
        Publisher updatedPublisher = publisherRepository.save(publisher);
        return ResponseEntity.ok(updatedPublisher);
    }

    // ------------------------
    // DELETE: Eliminar un publisher por ID
    // ------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!publisherRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // NOTA: Si Publisher tiene Comics, se eliminarán automáticamente
        // debido a orphanRemoval=true en Publisher.java
        publisherRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
