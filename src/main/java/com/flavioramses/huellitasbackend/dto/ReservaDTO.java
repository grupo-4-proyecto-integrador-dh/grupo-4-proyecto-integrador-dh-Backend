package com.flavioramses.huellitasbackend.dto;

import com.flavioramses.huellitasbackend.model.Reserva;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class ReservaDTO {
    private Long id;
    private String fechaDesde;
    private String fechaHasta;
    private String mascotaNombre;
    private Long mascotaId;
    private String alojamientoNombre;
    private Long alojamientoId;
    private Double alojamientoPrecio;
    private String clienteNombre;
    private String clienteApellido;
    private String clienteEmail;

    public ReservaDTO(Reserva reserva) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Define el formato de fecha

        this.id = reserva.getId();
        this.fechaDesde = reserva.getFechaDesde() != null ? reserva.getFechaDesde().format(formatter) : "Fecha no disponible";
        this.fechaHasta = reserva.getFechaHasta() != null ? reserva.getFechaHasta().format(formatter) : "Fecha no disponible";
        this.mascotaNombre = reserva.getMascota().getNombre();
        this.mascotaId = reserva.getMascota().getId();
        this.alojamientoId = reserva.getAlojamiento().getId();
        this.alojamientoNombre = reserva.getAlojamiento().getNombre();
        this.alojamientoPrecio = reserva.getAlojamiento().getPrecio();
        this.clienteNombre = reserva.getCliente().getUsuario().getNombre();
        this.clienteApellido = reserva.getCliente().getUsuario().getApellido();
        this.clienteEmail = reserva.getCliente().getUsuario().getEmail();
    }

    public static ReservaDTO toReservaDTO(Reserva reserva){
        return new ReservaDTO(reserva);
    }

    public static List<ReservaDTO> toReservaDTOList(List<Reserva> reservas) {
        return reservas.stream()
                .map(ReservaDTO::toReservaDTO)
                .collect(Collectors.toList());
    }
}