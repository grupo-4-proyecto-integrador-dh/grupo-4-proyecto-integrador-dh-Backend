package com.flavioramses.huellitasbackend.dto;

import com.flavioramses.huellitasbackend.model.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private boolean esFavorito;
    private List<ReservaDTO> reservas;
    private List<LocalDate[]> fechasOcupadas;

    public AlojamientoDTO(Long id, String nombre, String descripcion, Double precio, Long categoriaId, List<String> imagenesUrl, List<ReservaDTO> reservas) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.categoriaId = categoriaId;
        this.imagenesUrl = imagenesUrl;
        this.reservas = reservas;
        this.esFavorito = false;
        this.fechasOcupadas = new ArrayList<>();
    }
}
