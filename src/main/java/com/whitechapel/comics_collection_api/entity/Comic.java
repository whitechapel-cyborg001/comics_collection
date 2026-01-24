package com.whitechapel.comics_collection_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comic")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;           // e.g., "Amazing Fantasy #15"

    @Positive(message = "Issue number must be positive")
    @Column(nullable = false)
    private Integer issueNumber;    // e.g., 15

    @Positive(message = "Year must be positive")
    @Column(nullable = false)
    private Integer year;           // e.g., 1962

    private String variant;         // "Standard", "Variant A"

    @ManyToOne
    private Publisher publisher;

    // ------------------------
    // Relaciones JPA
    // ------------------------
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "series_id")
    private Series series;

    @ManyToMany
    @JoinTable(
        name = "comic_creators",
        joinColumns = @JoinColumn(name = "comic_id"),
        inverseJoinColumns = @JoinColumn(name = "creator_id")
    )
    private Set<Creator> creators = new HashSet<>();

    // ------------------------
    // Campos personales de la colección
    // ------------------------
    @Column(nullable = false)
    private boolean owned = true;

    private String condition;       // "Near Mint", "Very Fine"

    @Positive(message = "Purchase price must be positive")
    private Double purchasePrice;

    @PastOrPresent(message = "Purchase date must be in the past or present")
    private LocalDate purchaseDate;

    @Column(length = 1000)
    private String notes;           // "Firmado por...", "Primera aparición"

    private String coverImageUrl;   // URL de la portada
}
