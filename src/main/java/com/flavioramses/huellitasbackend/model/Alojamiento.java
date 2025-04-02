package com.flavioramses.huellitasbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "alojamientos")
public class Alojamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;
    private String descripcion;
    private Double precio;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @OneToMany(mappedBy = "alojamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ImagenAlojamiento> imagenes;

    @JsonIgnore
    @OneToMany(mappedBy = "alojamiento")
    @Builder.Default
    private List<Reserva> reservas = new ArrayList<>();

    @Column(nullable = false)
    private boolean activo = true;

    @JsonIgnore
    @OneToMany(mappedBy = "alojamiento")
    private List<AlojamientoFavorito> favoritos = new ArrayList<>();

    @Transient // No se almacena en la BD, se calcula en tiempo de ejecución
    private boolean disponible;

    public boolean estaDisponible(LocalDate fechaDesde, LocalDate fechaHasta) {
        return reservas.stream()
                .noneMatch(reserva ->
                        (reserva.getEstado() == EstadoReserva.PENDIENTE || reserva.getEstado() == EstadoReserva.CONFIRMADA) &&
                                !(fechaHasta.isBefore(reserva.getFechaDesde()) || fechaDesde.isAfter(reserva.getFechaHasta()))
                );
    }
}