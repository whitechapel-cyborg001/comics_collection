package com.whitechapel.comics_collection_api.controller;

import com.whitechapel.comics_collection_api.entity.Comic;
import com.whitechapel.comics_collection_api.repository.ComicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/comics")
public class ComicController {

    @Autowired
    private ComicRepository comicRepository;

    // GET todos con paginación
    @GetMapping
    public Page<Comic> getAllComics(Pageable pageable) {
        return comicRepository.findAll(pageable);
    }

    // GET por ID
    @GetMapping("/{id}")
    public ResponseEntity<Comic> getComicById(@PathVariable Long id) {
        Optional<Comic> comic = comicRepository.findById(id);
        return comic.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST crear nuevo cómic
    @PostMapping
    public Comic createComic(@Valid @RequestBody Comic comic) {
        return comicRepository.save(comic);
    }

    // PUT actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Comic> updateComic(@PathVariable Long id, @Valid @RequestBody Comic comicDetails) {
        Optional<Comic> optionalComic = comicRepository.findById(id);
        if (optionalComic.isPresent()) {
            Comic comic = optionalComic.get();
            comic.setTitle(comicDetails.getTitle());
            comic.setIssueNumber(comicDetails.getIssueNumber());
            comic.setYear(comicDetails.getYear());
            comic.setVariant(comicDetails.getVariant());
            comic.setSeries(comicDetails.getSeries());
            comic.setCreators(comicDetails.getCreators());
            comic.setOwned(comicDetails.isOwned());
            comic.setCondition(comicDetails.getCondition());
            comic.setPurchasePrice(comicDetails.getPurchasePrice());
            comic.setPurchaseDate(comicDetails.getPurchaseDate());
            comic.setNotes(comicDetails.getNotes());
            comic.setCoverImageUrl(comicDetails.getCoverImageUrl());
            return ResponseEntity.ok(comicRepository.save(comic));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComic(@PathVariable Long id) {
        if (comicRepository.existsById(id)) {
            comicRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}