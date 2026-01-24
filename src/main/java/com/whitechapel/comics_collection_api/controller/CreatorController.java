package com.whitechapel.comics_collection_api.controller;

import com.whitechapel.comics_collection_api.entity.Creator;
import com.whitechapel.comics_collection_api.repository.CreatorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controller REST para gestionar creadores (autores, ilustradores, etc.)
 * Proporciona endpoints CRUD para la entidad Creator.
 *
 * Buenas prácticas aplicadas:
 *  - @RestController + @RequestMapping
 *  - Inyección de dependencias vía constructor
 *  - @Valid para validaciones de entidades
 *  - ResponseEntity para devolver códigos HTTP adecuados
 *  - Comentarios explicativos en cada método
 */
@RestController
@RequestMapping("/api/creators")
public class CreatorController {

    /**
     * Repository de Creator inyectado por Spring.
     * Permite operaciones CRUD sobre la entidad Creator.
     */
    private final CreatorRepository creatorRepository;

    @Autowired
    public CreatorController(CreatorRepository creatorRepository) {
        this.creatorRepository = creatorRepository;
    }

    // ------------------------
    // GET: Obtener todos los creadores
    // ------------------------
    @GetMapping
    public List<Creator> getAllCreators() {
        return creatorRepository.findAll();
    }

    // ------------------------
    // GET: Obtener un creador por ID
    // ------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Creator> getCreatorById(@PathVariable Long id) {
        Optional<Creator> creator = creatorRepository.findById(id);
        if (creator.isPresent()) {
            return ResponseEntity.ok(creator.get()); // 200 OK con el creador
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found si no existe
        }
    }

    // ------------------------
    // POST: Crear un nuevo creador
    // ------------------------
    @PostMapping
    public ResponseEntity<Creator> createCreator(@Valid @RequestBody Creator creator) {
        // @Valid activa las validaciones definidas en Creator.java
        Creator savedCreator = creatorRepository.save(creator);
        return ResponseEntity.ok(savedCreator);
    }

    // ------------------------
    // PUT: Actualizar un creador existente
    // ------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Creator> updateCreator(
            @PathVariable Long id,
            @Valid @RequestBody Creator creatorDetails) {

        Optional<Creator> optionalCreator = creatorRepository.findById(id);
        if (!optionalCreator.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Creator creator = optionalCreator.get();
        // Actualizamos solo los campos permitidos
        creator.setName(creatorDetails.getName());
        // No modificamos la relación Comics directamente desde aquí
        // Para eso se deberían usar endpoints separados si se desea

        Creator updatedCreator = creatorRepository.save(creator);
        return ResponseEntity.ok(updatedCreator);
    }

    // ------------------------
    // DELETE: Eliminar un creador por ID
    // ------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreator(@PathVariable Long id) {
        if (!creatorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        creatorRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
