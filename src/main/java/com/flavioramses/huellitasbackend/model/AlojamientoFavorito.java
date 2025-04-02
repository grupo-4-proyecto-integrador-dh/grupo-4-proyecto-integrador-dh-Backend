package com.flavioramses.huellitasbackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad que representa un alojamiento marcado como favorito por un cliente.
 * Se establece una restricción de unicidad para que un cliente no pueda marcar
 * el mismo alojamiento como favorito más de una vez.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "alojamientos_favoritos", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cliente_id", "alojamiento_id"})
})
public class AlojamientoFavorito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alojamiento_id", nullable = false)
    private Alojamiento alojamiento;

    @Column(name = "fecha_marcado")
    private LocalDateTime fechaMarcado;
    
    /**
     * Método de fábrica para crear una nueva instancia de AlojamientoFavorito.
     * 
     * @param cliente El cliente que marca el alojamiento como favorito
     * @param alojamiento El alojamiento a marcar como favorito
     * @return Una nueva instancia de AlojamientoFavorito
     */
    public static AlojamientoFavorito crear(Cliente cliente, Alojamiento alojamiento) {
        return AlojamientoFavorito.builder()
                .cliente(cliente)
                .alojamiento(alojamiento)
                .fechaMarcado(LocalDateTime.now())
                .build();
    }
}