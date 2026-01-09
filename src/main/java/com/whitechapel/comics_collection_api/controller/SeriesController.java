package com.whitechapel.comics_collection_api.controller;

import com.whitechapel.comics_collection_api.entity.Series;
import com.whitechapel.comics_collection_api.repository.SeriesRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series")
public class SeriesController {

    @Autowired
    private SeriesRepository seriesRepository;

    @GetMapping
    public List<Series> getAll() {
        return seriesRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Series> getById(@PathVariable Long id) {
        return seriesRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Series create(@Valid @RequestBody Series series) {
        return seriesRepository.save(series);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Series> update(@PathVariable Long id, @Valid @RequestBody Series details) {
        return seriesRepository.findById(id)
                .map(series -> {
                    series.setTitle(details.getTitle());
                    series.setPublisher(details.getPublisher());
                    return ResponseEntity.ok(seriesRepository.save(series));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (seriesRepository.existsById(id)) {
            seriesRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}