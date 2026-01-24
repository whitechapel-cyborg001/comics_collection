package com.whitechapel.comics_collection_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity que representa una editorial o publicador de cómics.
 * Cada publisher puede publicar muchos cómics.
 *
 * Buenas prácticas aplicadas:
 *  - @Entity y @Table con nombre explícito
 *  - Lombok: @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder
 *  - Validaciones en campos obligatorios
 *  - Relación OneToMany con Comic
 *  - Comentarios explicativos para aprendizaje
 */
@Entity
@Table(name = "publisher") // Nombre explícito de la tabla en DB
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Publisher {

    /**
     * Clave primaria de la entidad.
     * Generada automáticamente por PostgreSQL (SERIAL/IDENTITY)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de la editorial.
     * Obligatorio y único para evitar duplicados.
     */
    @NotBlank(message = "Publisher name is required")
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * Relación OneToMany con la entidad Comic.
     * Una editorial puede publicar muchos cómics.
     * mappedBy indica que la columna 'publisher' está en la entidad Comic.
     * cascade = ALL permite que operaciones en Publisher se propaguen a sus cómics.
     * orphanRemoval = true elimina automáticamente los cómics que se quiten de la colección.
     */
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comic> comics = new HashSet<>();
}
