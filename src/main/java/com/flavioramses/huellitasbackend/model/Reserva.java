package com.flavioramses.huellitasbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference(value = "reserva-mascota")
    @ManyToOne
    @JoinColumn(name = "mascota_id", referencedColumnName = "id", nullable = false)
    private Mascota mascota;
    @JsonBackReference(value = "reserva-alojamiento")
    @ManyToOne
    @JoinColumn(name = "alojamiento_id", referencedColumnName = "id", nullable = false)
    private Alojamiento alojamiento;
    @JsonBackReference(value = "reserva-cliente")
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id", nullable = false)
    private Cliente cliente;

    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private LocalTime horaDesde;
    private LocalTime horaHasta;
}
