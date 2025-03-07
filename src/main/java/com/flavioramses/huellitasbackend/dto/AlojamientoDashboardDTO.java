package com.flavioramses.huellitasbackend.dto;

import com.flavioramses.huellitasbackend.model.Alojamiento;
import com.flavioramses.huellitasbackend.model.Categoria;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class AlojamientoDashboardDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String categoriaNombre; // Nombres de todas las categorías concatenados
    private List<String> imagenUrl; // Cambiado a List<String>

    public static AlojamientoDashboardDTO toAlojamientoDashboardDTO(Alojamiento alojamiento) {
        AlojamientoDashboardDTO dto = new AlojamientoDashboardDTO();
        dto.setId(alojamiento.getId());
        dto.setNombre(alojamiento.getNombre());
        dto.setDescripcion(alojamiento.getDescripcion());
        dto.setPrecio(alojamiento.getPrecio());

        // Obtener el nombre de la categoría
        Categoria categoria = alojamiento.getCategoria();
        if (categoria != null) {
            dto.setCategoriaNombre(categoria.getNombre());
        } else {
            dto.setCategoriaNombre("Sin categoría"); // Valor por defecto
        }

        // Obtener la lista de URLs de imágenes
        if (alojamiento.getImagenUrl() != null && !alojamiento.getImagenUrl().isEmpty()) {
            dto.setImagenUrl(alojamiento.getImagenUrl());
        } else {
            dto.setImagenUrl(List.of()); // Valor por defecto (lista vacía)
        }

        return dto;
    }
}