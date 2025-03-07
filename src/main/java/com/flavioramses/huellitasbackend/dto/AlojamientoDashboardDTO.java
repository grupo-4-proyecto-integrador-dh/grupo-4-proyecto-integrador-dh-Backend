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
    private String imagenUrl;

    public static AlojamientoDashboardDTO toAlojamientoDashboardDTO(Alojamiento alojamiento) {
        AlojamientoDashboardDTO dto = new AlojamientoDashboardDTO();
        dto.setId(alojamiento.getId());
        dto.setNombre(alojamiento.getNombre());
        dto.setDescripcion(alojamiento.getDescripcion());
        dto.setPrecio(alojamiento.getPrecio());

        // Concatenar los nombres de todas las categorías
        List<Categoria> categorias = alojamiento.getCategorias();
        if (categorias != null && !categorias.isEmpty()) {
            String nombres = categorias.stream()
                    .map(Categoria::getNombre)
                    .collect(Collectors.joining(", "));
            dto.setCategoriaNombre(nombres);
        } else {
            dto.setCategoriaNombre("Sin categoría"); // Valor por defecto
        }

        dto.setImagenUrl(alojamiento.getImagenUrl());
        return dto;
    }
}