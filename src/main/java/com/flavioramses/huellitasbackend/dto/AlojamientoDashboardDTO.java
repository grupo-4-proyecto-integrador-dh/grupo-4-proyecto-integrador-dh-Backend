package com.flavioramses.huellitasbackend.dto;

import com.flavioramses.huellitasbackend.model.Alojamiento;
import lombok.Data;

@Data
public class AlojamientoDashboardDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String categoriaNombre;
    private String imagenUrl;


    public static AlojamientoDashboardDTO toAlojamientoDashboardDTO(Alojamiento alojamiento) {
        AlojamientoDashboardDTO dto = new AlojamientoDashboardDTO();
        dto.setId(alojamiento.getId());
        dto.setNombre(alojamiento.getNombre());
        dto.setDescripcion(alojamiento.getDescripcion());
        dto.setPrecio(alojamiento.getPrecio());
        dto.setCategoriaNombre(alojamiento.getCategoria().getNombre());
        dto.setImagenUrl(alojamiento.getImagenUrl());
        return dto;
    }
}