package com.whitechapel.comics_collection_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Series {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;  // e.g., "Amazing Spider-Man"

    @ManyToOne
    private Publisher publisher;
}