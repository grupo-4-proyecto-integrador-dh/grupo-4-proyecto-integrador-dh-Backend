package com.flavioramses.huellitasbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;
    @ManyToOne
    @JoinColumn(name = "alojamiento_id", nullable = false)
    private Alojamiento alojamiento;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private LocalTime horaDesde;
    private LocalTime horaHasta;
}
