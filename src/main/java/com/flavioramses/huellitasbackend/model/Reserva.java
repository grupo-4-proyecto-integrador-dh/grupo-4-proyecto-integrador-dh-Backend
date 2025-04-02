package com.flavioramses.huellitasbackend.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.flavioramses.huellitasbackend.dto.ReservaDTO;
import com.flavioramses.huellitasbackend.dto.ReservaNuevaDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alojamiento_id", nullable = false)
    private Alojamiento alojamiento;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "fecha_desde", nullable = false)
    private LocalDate fechaDesde;

    @Column(name = "fecha_hasta", nullable = false)
    private LocalDate fechaHasta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReserva estado = EstadoReserva.PENDIENTE;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    public Reserva(Long id, Mascota mascota, Alojamiento alojamiento, Cliente cliente, LocalDate fechaDesde, LocalDate fechaHasta, EstadoReserva estado, LocalDateTime fechaCreacion) {
        this.id = id;
        this.mascota = mascota;
        this.alojamiento = alojamiento;
        this.cliente = cliente;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    public Reserva(Mascota mascota, Alojamiento alojamiento, Cliente cliente, LocalDate fechaDesde, LocalDate fechaHasta) {
        this.mascota = mascota;
        this.alojamiento = alojamiento;
        this.cliente = cliente;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.estado = EstadoReserva.PENDIENTE;
        this.fechaCreacion = LocalDateTime.now();
    }

    public ReservaDTO toReservaDTO() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new ReservaDTO(
                this.id,
                this.fechaDesde.format(formatter),
                this.fechaHasta.format(formatter),
                this.mascota.getNombre(),
                this.mascota.getId(),
                this.alojamiento.getNombre(),
                this.alojamiento.getId(),
                this.alojamiento.getPrecio(),
                this.cliente.getUsuario().getNombre(),
                this.cliente.getUsuario().getApellido(),
                this.cliente.getUsuario().getEmail(),
                this.estado,
                this.fechaCreacion.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }
}


