package com.whitechapel.comics_collection_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  // e.g., "Stan Lee"
    private String role;  // "Writer", "Artist", etc. (opcional)

    @ManyToMany(mappedBy = "creators")
    private List<Comic> comics;
}