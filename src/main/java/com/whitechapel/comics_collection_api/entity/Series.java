package com.whitechapel.comics_collection_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Data
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;  // e.g., "Amazing Spider-Man"

    @Positive(message = "numbers must be positive")
    private int numbers; // Full collection number

    @ManyToOne
    private Publisher publisher;
}