package com.whitechapel.comics_collection_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity que representa una Serie de cómics.
 * Cada serie pertenece a un Publisher y puede tener muchos Comics.
 *
 * Buenas prácticas aplicadas:
 *  - @Entity + @Table con nombre explícito
 *  - Relación ManyToOne con Publisher con JoinColumn y fetch LAZY
 *  - Uso de Integer para números opcional o int si es obligatorio
 *  - Lombok: @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder
 *  - Comentarios educativos
 */
@Entity
@Table(name = "series") // Nombre explícito de la tabla
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Series {

    /**
     * Clave primaria de la entidad.
     * Generada automáticamente por PostgreSQL (SERIAL/IDENTITY)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Título de la serie, obligatorio.
     */
    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;  // e.g., "Amazing Spider-Man"

    /**
     * Número total de cómics en la colección.
     * Obligatorio, positivo
     */
    @Positive(message = "Numbers must be positive")
    @Column(nullable = false)
    private int numbers; // Full collection number

    /**
     * Relación ManyToOne con Publisher.
     * Cada serie pertenece a un Publisher.
     * fetch=LAZY para evitar cargar Publisher automáticamente al consultar Series.
     * joinColumn especifica el nombre de la columna foreign key en la tabla series.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    /**
     * Relación OneToMany con Comic.
     * Una serie puede contener muchos comics.
     * mappedBy indica que la columna 'series' está en la entidad Comic.
     * cascade y orphanRemoval se podrían añadir según necesidad.
     */
    @OneToMany(mappedBy = "series", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comic> comics = new HashSet<>();
}
