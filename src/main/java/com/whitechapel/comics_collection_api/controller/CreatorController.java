package com.whitechapel.comics_collection_api.controller;

import com.whitechapel.comics_collection_api.entity.Creator;
import com.whitechapel.comics_collection_api.repository.CreatorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/creators")
public class CreatorController {

    @Autowired
    private CreatorRepository creatorRepository;

    @GetMapping
    public List<Creator> getAll() {
        return creatorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Creator> getById(@PathVariable Long id) {
        return creatorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Creator create(@Valid @RequestBody Creator creator) {
        return creatorRepository.save(creator);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Creator> update(@PathVariable Long id, @Valid @RequestBody Creator details) {
        return creatorRepository.findById(id)
                .map(creator -> {
                    creator.setName(details.getName());
                    creator.setRole(details.getRole());
                    return ResponseEntity.ok(creatorRepository.save(creator));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (creatorRepository.existsById(id)) {
            creatorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}