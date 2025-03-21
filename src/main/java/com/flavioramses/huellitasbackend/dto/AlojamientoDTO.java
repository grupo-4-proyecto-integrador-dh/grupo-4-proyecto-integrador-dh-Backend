package com.flavioramses.huellitasbackend.dto;

import com.flavioramses.huellitasbackend.model.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlojamientoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Long categoriaId;
    private List<String> imagenesUrl;
    private List<ReservaDTO> reservas;
}