package com.whitechapel.comics_collection_api.controller;

import com.whitechapel.comics_collection_api.entity.Publisher;
import com.whitechapel.comics_collection_api.repository.PublisherRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public Page<Publisher> getAll(Pageable pageable) {
        return publisherRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getById(@PathVariable Long id) {
        return publisherRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Publisher create(@Valid @RequestBody Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> update(@PathVariable Long id, @Valid @RequestBody Publisher details) {
        Optional<Publisher> optional = publisherRepository.findById(id);
        if (optional.isPresent()) {
            Publisher publisher = optional.get();
            publisher.setName(details.getName());
            return ResponseEntity.ok(publisherRepository.save(publisher));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (publisherRepository.existsById(id)) {
            publisherRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}