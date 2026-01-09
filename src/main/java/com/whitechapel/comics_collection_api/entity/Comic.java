package com.whitechapel.comics_collection_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Comic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;           // e.g., "Amazing Fantasy #15"
    @Positive(message = "Issue number must be positive")
    private Integer issueNumber;    // e.g., 15
    @Positive(message = "Year must be positive")
    private Integer year;           // e.g., 1962
    private String variant;         // "Standard", "Variant A"

    @ManyToOne
    private Series series;

    @ManyToMany
    @JoinTable(name = "comic_creators")
    private List<Creator> creators;

    // Campos personales de TU colección
    private boolean owned = true;
    private String condition;       // "Near Mint", "Very Fine"

    @Positive(message = "Purchase price must be positive")
    private Double purchasePrice;
    @PastOrPresent(message = "Purchase date must be in the past or present")
    private LocalDate purchaseDate;
    private String notes;           // "Firmado por...", "Primera aparición"
    private String coverImageUrl;   // URL de la portada (puedes poner links por ahora)
}