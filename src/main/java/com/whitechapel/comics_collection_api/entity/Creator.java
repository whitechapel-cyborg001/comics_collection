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
 * Entity que representa un creador de cómics (autor, ilustrador, etc.)
 * Se relaciona con los cómics que ha creado mediante una relación ManyToMany.
 */
@Entity
@Table(name = "creator") // Nombre explícito de la tabla en la base de datos
@Data // Lombok: genera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok: constructor vacío necesario para JPA
@AllArgsConstructor // Lombok: constructor completo con todos los campos
@Builder // Lombok: permite construir objetos con el patrón Builder
public class Creator {

    /**
     * Clave primaria de la entidad.
     * Generada automáticamente por PostgreSQL con IDENTITY (SERIAL)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del creador.
     * Obligatorio (no puede ser nulo ni vacío).
     */
    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    /**
     * Relación ManyToMany inversa con los cómics que ha creado.
     * La tabla intermedia es 'comic_creators' definida en Comic.java.
     * Se usa Set para evitar duplicados.
     */
    @ManyToMany(mappedBy = "creators")
    private Set<Comic> comics = new HashSet<>();
}
