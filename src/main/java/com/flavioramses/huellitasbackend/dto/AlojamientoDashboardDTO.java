package com.flavioramses.huellitasbackend.dto;

import com.flavioramses.huellitasbackend.model.Alojamiento;
import com.flavioramses.huellitasbackend.model.ImagenAlojamiento;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class AlojamientoDashboardDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String categoriaNombre;
    private List<String> imagenesUrl; // Cambiado a una lista de URLs

    public static AlojamientoDashboardDTO toAlojamientoDashboardDTO(Alojamiento alojamiento) {
        AlojamientoDashboardDTO dto = new AlojamientoDashboardDTO();
        dto.setId(alojamiento.getId());
        dto.setNombre(alojamiento.getNombre());
        dto.setDescripcion(alojamiento.getDescripcion());
        dto.setPrecio(alojamiento.getPrecio());
        dto.setCategoriaNombre(alojamiento.getCategoria().getNombre());
        dto.setImagenesUrl(alojamiento.getImagenes().stream()
                .map(ImagenAlojamiento::getUrlImagen)
                .collect(Collectors.toList()));
        return dto;
    }
}