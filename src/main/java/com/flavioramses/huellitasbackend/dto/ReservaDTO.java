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
    private List<String> categoriaNombre; // Lista de nombres de categorías

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

        // Obtener los nombres de las categorías
        this.categoriaNombre = reserva.getAlojamiento()
                .getCategorias()
                .stream()
                .map(categoria -> categoria.getNombre()) // Obtener el nombre de cada categoría
                .collect(Collectors.toList());

        this.clienteNombre = reserva.getCliente().getUsuario().getNombre();
        this.clienteApellido = reserva.getCliente().getUsuario().getApellido();
        this.clienteEmail = reserva.getCliente().getUsuario().getEmail();
    }

    public static ReservaDTO toReservaDTO(Reserva reserva) {
        return new ReservaDTO(reserva);
    }

    public static List<ReservaDTO> toReservaDTOList(List<Reserva> reservas) {
        return reservas.stream()
                .map(ReservaDTO::toReservaDTO)
                .collect(Collectors.toList());
    }
}