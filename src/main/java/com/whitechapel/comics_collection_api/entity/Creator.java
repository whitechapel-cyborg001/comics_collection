package com.whitechapel.comics_collection_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Creator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Name is required")
    private String name;  // e.g., "Stan Lee"
    private String role;  // "Writer", "Artist", etc. (opcional)

    @ManyToMany(mappedBy = "creators")
    private List<Comic> comics;
}