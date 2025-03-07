package com.flavioramses.huellitasbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    private String descripcion;

    @JsonIgnore
    @OneToMany(mappedBy = "categoria") // Agregado OneToMany
    private List<Alojamiento> alojamientos;
}