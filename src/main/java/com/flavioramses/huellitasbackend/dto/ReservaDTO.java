package com.flavioramses.huellitasbackend.dto;

import com.flavioramses.huellitasbackend.model.Reserva;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ReservaDTO {
    private Long id;
    private String fechaDesde;
    private String fechaHasta;
    private String horaDesde;
    private String horaHasta;

    private String mascotaNombre;
    private Long mascotaId;

    private String alojamientoNombre;
    private Long alojamientoId;
    private Double alojamientoPrecio;
    private String categoriaNombre; // Cambiado a String para una sola categoría

    private String clienteNombre;
    private String clienteApellido;
    private String clienteEmail;

    public ReservaDTO(Reserva reserva) {
        this.id = reserva.getId();
        this.fechaDesde = reserva.getFechaDesde().toString();
        this.fechaHasta = reserva.getFechaHasta().toString();
        this.horaDesde = reserva.getHoraDesde().toString();
        this.horaHasta = reserva.getHoraHasta().toString();
        this.mascotaNombre = reserva.getMascota().getNombre();
        this.mascotaId = reserva.getMascota().getId();
        this.alojamientoNombre = reserva.getAlojamiento().getNombre();
        this.alojamientoId = reserva.getAlojamiento().getId();
        this.alojamientoPrecio = reserva.getAlojamiento().getPrecio();
        this.categoriaNombre = reserva.getAlojamiento().getCategoria().getNombre(); // Obtiene el nombre de la categoría
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