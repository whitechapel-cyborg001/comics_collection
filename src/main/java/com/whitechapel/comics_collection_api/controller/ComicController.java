package com.whitechapel.comics_collection_api.controller;

import com.whitechapel.comics_collection_api.entity.Comic;
import com.whitechapel.comics_collection_api.repository.ComicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller REST para gestionar Comics.
 * Proporciona endpoints CRUD para la entidad Comic.
 *
 * Buenas prácticas aplicadas:
 *  - @RestController + @RequestMapping
 *  - Constructor para inyección de dependencias
 *  - @Valid para validar datos según anotaciones de Comic.java
 *  - ResponseEntity para controlar códigos HTTP
 *  - Comentarios explicativos en cada endpoint
 */
@RestController
@RequestMapping("/api/comics")
public class ComicController {

    private final ComicRepository comicRepository;

    /**
     * Inyección de dependencias vía constructor
     */
    @Autowired
    public ComicController(ComicRepository comicRepository) {
        this.comicRepository = comicRepository;
    }

    // ------------------------
    // GET: Obtener todos los cómics con paginación
    // ------------------------
    @GetMapping
    public Page<Comic> getAllComics(Pageable pageable) {
        // Pageable permite solicitar páginas con ?page=0&size=10
        return comicRepository.findAll(pageable);
    }

    // ------------------------
    // GET: Obtener un cómic por ID
    // ------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Comic> getComicById(@PathVariable Long id) {
        Optional<Comic> optionalComic = comicRepository.findById(id);
        return optionalComic
                .map(ResponseEntity::ok)          // 200 OK si existe
                .orElse(ResponseEntity.notFound().build()); // 404 si no existe
    }

    // ------------------------
    // POST: Crear un nuevo cómic
    // ------------------------
    @PostMapping
    public ResponseEntity<Comic> createComic(@Valid @RequestBody Comic comic) {
        // @Valid activa las validaciones de Comic.java (NotBlank, Positive, etc.)
        Comic savedComic = comicRepository.save(comic);
        // Devuelve 201 CREATED y el objeto creado
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComic);
    }

    // ------------------------
    // PUT: Actualizar un cómic existente
    // ------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Comic> updateComic(@PathVariable Long id,
                                             @Valid @RequestBody Comic comicDetails) {

        Optional<Comic> optionalComic = comicRepository.findById(id);

        if (!optionalComic.isPresent()) {
            // 404 Not Found si el cómic no existe
            return ResponseEntity.notFound().build();
        }

        Comic comic = optionalComic.get();
        // Actualizamos campos permitidos
        comic.setTitle(comicDetails.getTitle());
        comic.setIssueNumber(comicDetails.getIssueNumber());
        comic.setYear(comicDetails.getYear());
        comic.setVariant(comicDetails.getVariant());
        comic.setSeries(comicDetails.getSeries());        // ManyToOne
        comic.setCreators(comicDetails.getCreators());    // ManyToMany
        comic.setOwned(comicDetails.isOwned());
        comic.setCondition(comicDetails.getCondition());
        comic.setPurchasePrice(comicDetails.getPurchasePrice());
        comic.setPurchaseDate(comicDetails.getPurchaseDate());
        comic.setNotes(comicDetails.getNotes());
        comic.setCoverImageUrl(comicDetails.getCoverImageUrl());

        // Guardamos cambios y devolvemos 200 OK
        Comic updatedComic = comicRepository.save(comic);
        return ResponseEntity.ok(updatedComic);
    }

    // ------------------------
    // DELETE: Eliminar un cómic por ID
    // ------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComic(@PathVariable Long id) {
        if (!comicRepository.existsById(id)) {
            // 404 Not Found si no existe
            return ResponseEntity.notFound().build();
        }

        // NOTA: Relaciones ManyToMany con creators y ManyToOne con series
        // se mantienen según reglas de JPA/Hibernate.
        comicRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
