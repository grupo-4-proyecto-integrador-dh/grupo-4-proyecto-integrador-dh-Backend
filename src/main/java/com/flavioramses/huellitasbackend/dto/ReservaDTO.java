package com.flavioramses.huellitasbackend.dto;

import com.flavioramses.huellitasbackend.model.*;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private EstadoReserva estado;
    private String fechaCreacion;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static ReservaDTO fromEntity(Reserva reserva) {
        if (reserva == null) {
            return null;
        }

        ReservaDTO dto = new ReservaDTO();
        dto.setId(reserva.getId());
        
        dto.setFechaDesde(reserva.getFechaDesde() != null ? 
            reserva.getFechaDesde().format(FORMATTER) : "Fecha no disponible");
        dto.setFechaHasta(reserva.getFechaHasta() != null ? 
            reserva.getFechaHasta().format(FORMATTER) : "Fecha no disponible");
        dto.setFechaCreacion(reserva.getFechaCreacion() != null ? 
            reserva.getFechaCreacion().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "Fecha no disponible");
        
        dto.setEstado(reserva.getEstado());
        
        if (reserva.getMascota() != null) {
            dto.setMascotaId(reserva.getMascota().getId());
            dto.setMascotaNombre(reserva.getMascota().getNombre());
        } else {
            dto.setMascotaId(null);
            dto.setMascotaNombre("Desconocida");
        }
        
        if (reserva.getAlojamiento() != null) {
            dto.setAlojamientoId(reserva.getAlojamiento().getId());
            dto.setAlojamientoNombre(reserva.getAlojamiento().getNombre());
            dto.setAlojamientoPrecio(reserva.getAlojamiento().getPrecio());
        } else {
            dto.setAlojamientoId(null);
            dto.setAlojamientoNombre("No disponible");
            dto.setAlojamientoPrecio(0.0);
        }
        
        if (reserva.getCliente() != null && reserva.getCliente().getUsuario() != null) {
            dto.setClienteNombre(reserva.getCliente().getUsuario().getNombre());
            dto.setClienteApellido(reserva.getCliente().getUsuario().getApellido());
            dto.setClienteEmail(reserva.getCliente().getUsuario().getEmail());
        } else {
            dto.setClienteNombre("No disponible");
            dto.setClienteApellido("No disponible");
            dto.setClienteEmail("No disponible");
        }

        return dto;
    }

    public static List<ReservaDTO> toReservaDTOList(List<Reserva> reservas) {
        return reservas != null ? 
            reservas.stream()
                .map(ReservaDTO::fromEntity)
                .filter(dto -> dto != null)
                .collect(Collectors.toList()) : 
            Collections.emptyList();
    }
}
