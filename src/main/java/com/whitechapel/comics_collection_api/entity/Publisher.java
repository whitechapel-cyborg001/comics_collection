package com.whitechapel.comics_collection_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // e.g., "Marvel", "DC Comics"
}