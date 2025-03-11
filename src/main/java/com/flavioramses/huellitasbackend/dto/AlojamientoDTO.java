package com.flavioramses.huellitasbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlojamientoDTO {

    private String nombre;
    private String descripcion;
    private Double precio;
    private Long categoriaId;
    private List<String> imagenesUrl;
}